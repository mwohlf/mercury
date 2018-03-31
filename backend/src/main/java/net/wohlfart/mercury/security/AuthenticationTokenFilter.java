package net.wohlfart.mercury.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.IOException;


/*
 * fetch jwt token from request and add userDetails if possible, also reset the security context
 *
 * see: https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/security/JwtAuthenticationTokenFilter.java
 */
@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService; // UserDetailsServiceImpl

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            final Authentication authentication = getAuthenticationFromRequest(servletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(servletRequest, servletResponse);
            resetAuthenticationAfterRequest();
        } catch (UsernameNotFoundException ex) {
            log.info("removing cookie for unknown user", ex);
            jwtTokenUtil.cookies("").toSingleValueMap().forEach(servletResponse::setHeader);
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.sendRedirect(SecurityConstants.HOME);
        } catch (ExpiredJwtException ex) {
            log.info("Security exception for user {} - {}", ex.getClaims().getSubject(), ex.getMessage());
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.debug("Exception " + ex.getMessage(), ex);
            servletResponse.sendRedirect(SecurityConstants.HOME);
        }
    }

    private Authentication getAuthenticationFromRequest(HttpServletRequest servletRequest) {
        final String resolvedToken = this.resolveTokenFromCookie(servletRequest);

        if (!StringUtils.hasText(resolvedToken)) {
            log.info("<getAuthenticationFromRequest> no token found");
            return null;
        }

        if (!jwtTokenUtil.validateToken(resolvedToken)) {
            log.info("<getAuthenticationFromRequest> token is invalid");
            return null;
        }

        final String username = this.jwtTokenUtil.getUsernameFromToken(resolvedToken);
        if (!StringUtils.hasText(username)) {
            log.info("<getAuthenticationFromRequest> no username found");
            return null;
        }

        log.info("<doFilterInternal> found user: '{}'", username);

        // see: https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/security/JwtAuthenticationTokenFilter.java
        // TODO: we need to store the data in the token to avoid db roundtrip
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
        logger.info("authenticated user '" + username + "', setting security context");
        return authentication;
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SecurityConstants.COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
