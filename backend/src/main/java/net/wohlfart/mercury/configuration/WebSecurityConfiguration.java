package net.wohlfart.mercury.configuration;

import net.wohlfart.mercury.security.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static net.wohlfart.mercury.SecurityConstants.*;


@Configuration
@EnableOAuth2Client // creates oauth2ClientContextFilter see: http://projects.spring.io/spring-security-oauth/docs/oauth2.html
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"net.wohlfart"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                //.authenticationProvider()
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    // see: http://www.svlada.com/jwt-token-authentication-with-spring-boot/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity     // implements HttpsSecurityBuilder
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()  // returns the SecurityBuilder
                .authorizeRequests()
                    .antMatchers(SIGNUP_ENDPOINT).permitAll() // sign in end-point allowed by anyone
                    .antMatchers(LOGIN_ENDPOINT).permitAll() // login end-point allowed by anyone
                    .antMatchers(REFRESH_ENDPOINT).permitAll() // token refresh end-point allowed by anyone
                    .antMatchers(H2_CONSOLE_URL).permitAll() // H2 Console Dash-board allowed by anyone
                    .antMatchers(API_DOCS).permitAll() // swagger api docs for code generation
                    // static resources, images, js, ...
                    .antMatchers(ROOT, HOME, ASSETS, JS_RESOURCES, FONTAWESOME).permitAll()
                    // SSO endpoint redirect target
                    .antMatchers(OAUTH_ENDPOINT + "/**").permitAll()
            .and()  // returns the SecurityBuilder
                .authorizeRequests()
                    .antMatchers(API + "/**").permitAll() //  API endpoints are protected on the method level
            .and()  // the singlepage routes for angular, need to server index.html
                .authorizeRequests()   // see IndexController
                    .antMatchers(ROOT, "/login", "/label", "/settings", "/admin").permitAll() //  API endpoints are protected on the method level
            .and()
                .authorizeRequests()
                    .antMatchers(CATCH_ALL).denyAll()  // the default behavior is to deny access
            .and()
              //  .addFilterBefore(facebookAuthenticationFilter, BasicAuthenticationFilter.class)
              //  .addFilterBefore(clientAuthenticationFilterImpl, BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)


            ;
    }

}
