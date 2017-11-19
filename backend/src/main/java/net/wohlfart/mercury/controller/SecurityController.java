package net.wohlfart.mercury.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.security.JwtTokenUtil;
import net.wohlfart.mercury.security.UserDetailsImpl;
import net.wohlfart.mercury.security.UserNotFoundException;
import net.wohlfart.mercury.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;

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
    @PostMapping(LOGIN_ENDPOINT)
    public ResponseEntity<PrincipalResponse> login(@RequestBody LoginRequest login) throws AuthenticationException {

        final UserDetailsImpl userDetails;

        try {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(login.username);
            // TODO: check password
            log.info("<login> success '{}'", login.username);
        } catch (UsernameNotFoundException | NoResultException ex) {
            log.info("<login> failed {}/{}", login.username, login.password);
            throw new UserNotFoundException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(jwtTokenUtil.cookies(userDetails));
        return new ResponseEntity<>(body(userDetails), headers, HttpStatus.OK);
    }

    @GetMapping(LOGIN_ENDPOINT)
    public ResponseEntity<PrincipalResponse> login(Principal principal) {
        final String useranme = principal.getName();
        final UserDetailsImpl userDetails;

        try {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(useranme);
        } catch (UsernameNotFoundException | NoResultException ex) {
            throw new UserNotFoundException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(jwtTokenUtil.cookies(userDetails));
        return new ResponseEntity<>(body(userDetails), headers, HttpStatus.OK);
    }

    @GetMapping(LOGOUT_ENDPOINT)
    public ResponseEntity<PrincipalResponse> logout(Principal principal) {
        final String useranme = principal.getName();
        final UserDetailsImpl userDetails;

        // TODO

        try {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(useranme);
        } catch (UsernameNotFoundException | NoResultException ex) {
            throw new UserNotFoundException(ex);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(jwtTokenUtil.cookies(""));
        return new ResponseEntity<>(body(userDetails), headers, HttpStatus.OK);
    }


    /**
     * Returns authentication token for given user
     * @param register with username and password
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNUP_ENDPOINT)
    public ResponseEntity<PrincipalResponse> signup(@RequestBody LoginRequest register) throws AuthenticationException {
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

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(jwtTokenUtil.cookies(userDetails));
        return new ResponseEntity<>(body(userDetails), headers, HttpStatus.OK);
    }

    /**
     * Refreshes token
     * @param request with old JWT
     * @return Refreshed JWT
     */
    @PostMapping(REFRESH_ENDPOINT)
    public ResponseEntity refresh(HttpServletRequest request) {
        final String refreshTokenString = request.getHeader(TOKEN_HEADER);
        final String tokenString = jwtTokenUtil.refreshToken(refreshTokenString);
        log.info("<refresh> refreshTokenString '{}' tokenString '{}'", refreshTokenString, tokenString);

        final HttpHeaders headers = new HttpHeaders();
        headers.putAll(jwtTokenUtil.cookies(tokenString));
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    private PrincipalResponse body(UserDetailsImpl userDetails) {
        return new PrincipalResponse(userDetails.getId(), userDetails.getUsername(), new String[] {});
    }

    @Data
    @AllArgsConstructor
    public static class LoginRequest implements Serializable {
        String username;
        String password;
    }


    @Data
    @AllArgsConstructor
    public static class PrincipalResponse implements Serializable {
        Long userId;
        String username;
        String[] roles;
    }

}
