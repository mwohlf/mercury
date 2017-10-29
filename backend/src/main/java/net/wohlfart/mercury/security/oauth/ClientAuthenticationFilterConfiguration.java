package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
public class ClientAuthenticationFilterConfiguration {


    @Bean
    public CompositeFilter initCompositeFilter() {
        CompositeFilter result = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), "/login/facebook"));
        filters.add(ssoFilter(github(), "/login/github"));
        filters.add(ssoFilter(google(), "/login/google"));
        result.setFilters(filters);
        return result;
    }

    private Filter ssoFilter(ClientResources clientResources, String path) {

        DefaultOAuth2ClientContext defaultOAuth2ClientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate template = new AuthTemplate(clientResources.getClient(), defaultOAuth2ClientContext);
       // template.setAuthenticator(new Authenticator());
       // template.setAccessTokenProvider(new OAuthAccessTokenProvider());

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(clientResources.getResource().getUserInfoUri(), clientResources.getClient().getClientId());
        tokenServices.setRestTemplate(template);

        OAuth2ClientAuthenticationProcessingFilter filter = new AuthFilter(path, defaultOAuth2ClientContext);
        filter.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties(prefix="github", ignoreUnknownFields = false)
    public ClientResources github() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties(prefix="facebook", ignoreUnknownFields = false)
    public ClientResources facebook() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties(prefix="google", ignoreUnknownFields = false)
    public ClientResources google() {
        return new ClientResources();
    }


    static class OAuthAccessTokenProvider implements AccessTokenProvider {

        @Override
        public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest parameters) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
            return null;
        }

        @Override
        public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
            return false;
        }

        @Override
        public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
            return null;
        }

        @Override
        public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
            return false;
        }

    }


    @Slf4j
    static class Authenticator implements OAuth2RequestAuthenticator {

        @Override
        public void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext, ClientHttpRequest request) {
            log.info("resource: " + resource);
            log.info("clientContext: " + clientContext);
            log.info("request: " + request);
        }
    }


    @Slf4j
    static class AuthTemplate extends OAuth2RestTemplate {

        public AuthTemplate(OAuth2ProtectedResourceDetails resource, DefaultOAuth2ClientContext context) {
            super(resource, context);
            log.info("<constructor> resource: " + resource);
        }

        @Override
        public OAuth2AccessToken getAccessToken() throws UserRedirectRequiredException {
            log.info("<getAccessToken> ");
            return super.getAccessToken();
        }

        @Override
        public OAuth2ClientContext getOAuth2ClientContext() {
            log.info("<getOAuth2ClientContext> ");
            return super.getOAuth2ClientContext();
        }

        @Override
        public void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
            log.info("<handleResponse> " + response);
            super.handleResponse(url, method, response);
            super.getAccessToken();
        }

        @Override
        public OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context) throws UserRedirectRequiredException {
            try {

                log.info("########## <acquireAccessToken> oauth2Context:" + oauth2Context);
                String uri = oauth2Context.getAccessTokenRequest().getCurrentUri();


                AccessTokenRequest request = oauth2Context.getAccessTokenRequest();
                String state = request.getStateKey();
                if (!StringUtils.isEmpty(state)) {
                //    request.setAuthorizationCode("dude");
                }

                log.info("########## <acquireAccessToken> uri:" + uri);
                OAuth2AccessToken result = super.acquireAccessToken(oauth2Context);
                log.info("##########      result:" + result);
                return result;
            } catch (Exception ex) {
                log.info("##########      ex: " + ex);
                throw ex;
            }
        }

    }

    @Slf4j
    static class AuthFilter extends OAuth2ClientAuthenticationProcessingFilter {

        private final DefaultOAuth2ClientContext defaultOAuth2ClientContext;

        public AuthFilter(String defaultFilterProcessesUrl, DefaultOAuth2ClientContext defaultOAuth2ClientContext) {
            super(defaultFilterProcessesUrl);
            this.defaultOAuth2ClientContext = defaultOAuth2ClientContext; // TODO: this is not threadsafe!!
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            log.info("<attemptAuthentication> " + request);

            String authCode = request.getParameter("code");
            if (!StringUtils.isEmpty(authCode)) {
                log.info("<attemptAuthentication> code: " + authCode);
                defaultOAuth2ClientContext.getAccessTokenRequest().setAuthorizationCode(authCode);
            }

            return super.attemptAuthentication(request, response);
        }

    }

}
