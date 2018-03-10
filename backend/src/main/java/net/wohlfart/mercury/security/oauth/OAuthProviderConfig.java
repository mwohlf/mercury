package net.wohlfart.mercury.security.oauth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.HashMap;


/**
 * there is one instance of this config for each OAuth provider
 *   - client: properties to implement the 3 legged OAuth dance
 *   - resource: uri to retrieve user data
 */
@Slf4j
@Data
@ConfigurationProperties
public class OAuthProviderConfig {

    @NestedConfigurationProperty
    public AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    public ResourceServerProperties resource = new ResourceServerProperties();


    @SuppressWarnings("WeakerAccess") // public for config
    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    @SuppressWarnings("WeakerAccess") // public for config
    public ResourceServerProperties getResource() {
        return resource;
    }

    public ResponseEntity redirectForAuthentication(String stateId) {
        return new AuthRedirectBuilder(this).state(stateId).build();   // redirect to provider
    }

    public String requestAccessToken(String code) {
        ResponseEntity<HashMap> accessTokenResponse =  new AccessTokenRetriever(this, code).request();
        log.info("<authenticate> {}", accessTokenResponse);
        log.info("<authenticate> body: {}", accessTokenResponse.getBody());
        return (String) accessTokenResponse.getBody().get("access_token");
    }
}
