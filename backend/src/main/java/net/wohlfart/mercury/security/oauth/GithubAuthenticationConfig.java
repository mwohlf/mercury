package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


@Slf4j
@Configuration
public class GithubAuthenticationConfig {

    public static final String URL = "/login/github";

    @Bean
    @Qualifier("github")
    public OAuth2ClientAuthenticationProcessingFilter authenticationFilter() {
        OAuth2ClientAuthenticationProcessingFilter result = new OAuthAuthenticationFilter(URL);
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(client());
        result.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resource().getUserInfoUri(), client().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        result.setTokenServices(tokenServices);
        return result;
    }

    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails client() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties resource() {
        return new ResourceServerProperties();
    }




}
