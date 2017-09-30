package net.wohlfart.mercury.configuration;

import net.wohlfart.mercury.security.auth.JwtAuthenticationTokenFilter;
import net.wohlfart.mercury.security.auth.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static net.wohlfart.mercury.SecurityConstants.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    private static final String SIGN_UP_URL = "login/";

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                //.authenticationProvider()
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {

    }
    */

    // see: http://www.svlada.com/jwt-token-authentication-with-spring-boot/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity     // implements HttpsSecurityBuilder
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                //.authenticationEntryPoint(this.authenticationEntryPoint)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .authorizeRequests()
                    .antMatchers(SIGN_UP_URL, SIGN_IN_URL).permitAll() // login end-point allowed by anyone
                    .antMatchers(TOKEN_REFRESH_URL).permitAll() // token refresh end-point allowed by anyone
                    .antMatchers(H2_CONSOLE_URL).permitAll() // H2 Console Dash-board allowed by anyone
            .and()
                .authorizeRequests()
                    .antMatchers(API_URL).authenticated() // Protected API End-points
            .and()
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class) // authentication filter

            ;
                // @formatter:on
        /*
                    .cors()  // add a cors filter via corsConfigurationSource to the builder and returns it
                .and()   // back to the builder
                    .csrf().disable() // returns the csrf configurer and removes it
                    .authorizeRequests() // returns the ExpressionInterceptUrlRegistry

                    .antMatchers(HttpMethod.POST, SIGN_UP_URL, SIGN_IN_URL).permitAll()  // url allowed by anyone
                    .anyRequest().authenticated() // any other request needs an authenticated user
                .and()  // returns the SecurityBuilder again
                .addFilter(authenticationTokenFilter)  // authentication filter
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));

        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
  ...
                ).permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().cacheControl();
        */
    }
}
