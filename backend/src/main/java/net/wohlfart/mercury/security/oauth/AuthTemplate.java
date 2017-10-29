package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;

@Slf4j
class AuthTemplate extends OAuth2RestTemplate {

    AuthTemplate(OAuth2ProtectedResourceDetails resource, DefaultOAuth2ClientContext context) {
        super(resource, context);
    }

    @Override
    public void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        log.debug("<handleResponse> for {} '{}'", method, url);
        super.handleResponse(url, method, response);
        log.debug("<handleResponse> trigger getAccessToken");
        super.getAccessToken();
    }

    /**
     * central method to retrieve access token, called twice
     *  - first time left by an exception because we need a redirect
     *  - second time it should return the token
     */
    @Override
    public OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context) throws UserRedirectRequiredException {
        log.debug("<acquireAccessToken> for request {}", oauth2Context.getAccessTokenRequest());
        return super.acquireAccessToken(oauth2Context);
    }

}
