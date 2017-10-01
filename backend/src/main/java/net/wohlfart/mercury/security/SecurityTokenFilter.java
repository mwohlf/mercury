package net.wohlfart.mercury.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
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
public class SecurityTokenFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("<doFilterInternal>");
        filterChain.doFilter(request, response);
    }

    /*
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new AuthenticationServiceException( "Authentication method not supported: " + request.getMethod());
        }

        // the request should contain the users credentials...
        ObjectMapper objectMapper = new ObjectMapper();
        UserForm userForm = objectMapper.readValue(toString(request.getInputStream()), UserForm.class);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userForm.username, userForm.password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authentication) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((UserDetailsImpl) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);

    }

    private String toString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }



    @Data
    public static class UserForm {

        String username;
        String password;

    }
    */
}