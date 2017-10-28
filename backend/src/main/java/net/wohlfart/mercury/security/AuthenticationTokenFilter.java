package net.wohlfart.mercury.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
 * fetch jwt token from request and add userDetails if possible
 *
 * see: https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/security/JwtAuthenticationTokenFilter.java
 */
@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            setupAuthenticationBeforeRequest(servletRequest);
            filterChain.doFilter(servletRequest, servletResponse);
            resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.debug("Exception " + eje.getMessage(), eje);
        }
    }

    private void setupAuthenticationBeforeRequest(HttpServletRequest servletRequest) {
        String resolvedToken = this.resolveToken(servletRequest);
        if (!StringUtils.hasText(resolvedToken)) {
            log.info("<setupAuthenticationBeforeRequest> no token found");
            return;
        }

        if (!jwtTokenUtil.validateToken(resolvedToken)) {
            log.info("<setupAuthenticationBeforeRequest> token is invalid");
            return;
        }

        String username = this.jwtTokenUtil.getUsernameFromToken(resolvedToken);
        log.info("<doFilterInternal> found user: {}", username);

        // see: https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/security/JwtAuthenticationTokenFilter.java
        // TODO: we need to store the data in the token to avoid db roundtrip
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
        logger.info("authenticated user " + username + ", setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length(), bearerToken.length());
        }
        return null;
    }

}