package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
public class ClientAuthenticationFilterConfiguration {

    public static final String FACEBOOK_FILTER_URI = "/login/facebook";
    public static final String GITHUB_FILTER_URI = "/login/github";
    public static final String GOOGLE_FILTER_URI = "/login/google";

    @Bean
    public CompositeFilter initCompositeFilter() {
        CompositeFilter result = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(createOAuthFilter(facebook(), FACEBOOK_FILTER_URI));
        filters.add(createOAuthFilter(github(), GITHUB_FILTER_URI));
        filters.add(createOAuthFilter(google(), GOOGLE_FILTER_URI));
        result.setFilters(filters);
        return result;
    }

    private Filter createOAuthFilter(OAuthProviderConfig clientResources, String path) {

        DefaultOAuth2ClientContext defaultOAuth2ClientContext = new DefaultOAuth2ClientContext();
        // OAuth2RestTemplate template = new AuthTemplate(clientResources.getClient(), defaultOAuth2ClientContext);
       // template.setAuthenticator(new Authenticator());
       // template.setAccessTokenProvider(new OAuthAccessTokenProvider());

        OAuth2RestTemplate restTemplate = new AuthTemplate(clientResources.getClient(), defaultOAuth2ClientContext);
        // OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(clientResources.getClient());

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(clientResources.getResource().getUserInfoUri(), clientResources.getClient().getClientId());
        tokenServices.setRestTemplate(restTemplate);

        OAuth2ClientAuthenticationProcessingFilter filter = new AuthFilter(path, defaultOAuth2ClientContext);
        filter.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties(prefix="github", ignoreUnknownFields = false)
    public OAuthProviderConfig github() {
        return new OAuthProviderConfig();
    }

    @Bean
    @ConfigurationProperties(prefix="facebook", ignoreUnknownFields = false)
    public OAuthProviderConfig facebook() {
        return new OAuthProviderConfig();
    }

    @Bean
    @ConfigurationProperties(prefix="google", ignoreUnknownFields = false)
    public OAuthProviderConfig google() {
        return new OAuthProviderConfig();
    }

}
