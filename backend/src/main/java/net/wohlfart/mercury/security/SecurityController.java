package net.wohlfart.mercury.security;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static net.wohlfart.mercury.SecurityConstants.*;


@Api
@Slf4j
@Controller
public class SecurityController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    /**
     * Adds new user and returns authentication token
     * @param login request with username, email and password fields
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(AUTHENTICATE_ENDPOINT)
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequest login) throws AuthenticationException {
        log.info("<authenticate> login '{}'", login);

        final UserDetailsImpl userDetails;

        try {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login.username);
        } catch (UsernameNotFoundException | NoResultException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException(ex);
        }

        return ResponseEntity.ok(createTokenResponse(userDetails));
    }

    /**
     * Returns authentication token for given user
     * @param register with username and password
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNUP_ENDPOINT)
    public ResponseEntity<TokenResponse> signup(@RequestBody AuthenticationRequest register) throws AuthenticationException {
        log.info("<signup> register '{}'", register);

        final UserDetailsImpl userDetails;

        final User user = User.builder().name(register.username).password(register.password).build();
        userService.save(user);

        try {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(register.username);
        } catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException(ex);
        }

        return ResponseEntity.ok(createTokenResponse(userDetails));
    }

    private TokenResponse createTokenResponse(UserDetailsImpl userDetails) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        final Authentication authentication = authenticationManager.authenticate(token);

        System.out.println("authentication success: " + authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String tokenString = jwtTokenUtil.generateToken(userDetails);
        return new TokenResponse(tokenString);
    }

    /**
     * Refreshes token
     * @param request with old JWT
     * @return Refreshed JWT
     */
    @PostMapping(REFRESH_ENDPOINT)
    public ResponseEntity<TokenResponse> refresh(HttpServletRequest request) {
        final String refreshTokenString = request.getHeader(TOKEN_HEADER);
        final String tokenString = jwtTokenUtil.refreshToken(refreshTokenString);
        log.info("<refresh> refreshTokenString '{}' tokenString '{}'", refreshTokenString, tokenString);
        return ResponseEntity.ok(new TokenResponse(tokenString));
    }

    @Data
    @AllArgsConstructor
    public static class  AuthenticationRequest implements Serializable {

        String username;
        String password;

    }

    @Data
    @AllArgsConstructor
    public static class TokenResponse implements Serializable {

        String token;

    }

}
