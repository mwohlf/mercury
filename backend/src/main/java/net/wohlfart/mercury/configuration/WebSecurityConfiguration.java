package net.wohlfart.mercury.configuration;

import net.wohlfart.mercury.security.SecurityTokenFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static net.wohlfart.mercury.SecurityConstants.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"net.wohlfart"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityTokenFilter authenticationTokenFilter;

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
               // .cors().disable() // CORS off
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                //.authenticationEntryPoint(this.authenticationEntryPoint)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(SIGNUP_ENDPOINT).permitAll() // sign in end-point allowed by anyone
                    .antMatchers(AUTHENTICATE_ENDPOINT).permitAll() // login end-point allowed by anyone
                    .antMatchers(REFRESH_ENDPOINT).permitAll() // token refresh end-point allowed by anyone
                    .antMatchers(H2_CONSOLE_URL).permitAll() // H2 Console Dash-board allowed by anyone
            .and()
                .authorizeRequests()
                    .antMatchers(API).authenticated() // Protected API End-points
            .and()
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class) // authentication filter
            ;
    }

}
