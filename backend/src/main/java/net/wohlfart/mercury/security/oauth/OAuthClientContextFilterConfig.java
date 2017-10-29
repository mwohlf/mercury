package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;


/**
 * the OAuth2ClientContextFilter configured here does
 *  - set the CURRENT_URI property
 *  - checks for UserRedirectRequiredException
 *  - set the STATE parameter for the redirect
 *  - triggers redirect with the help of the redirectStrategy
 */
@Slf4j
@Configuration
public class OAuthClientContextFilterConfig {

    public static final int ORDER = -100;

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(ORDER);
        return registration;
    }

}
