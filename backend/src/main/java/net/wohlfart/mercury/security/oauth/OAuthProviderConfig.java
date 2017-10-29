package net.wohlfart.mercury.security.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


/**
 * there is one instance of this config for each OAuth provider
 *   - client: properties to implement the 3 legged OAuth dance
 *   - resource: uri to retrieve user data
 */
@ConfigurationProperties
public class OAuthProviderConfig {

    @NestedConfigurationProperty
    AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    ResourceServerProperties resource = new ResourceServerProperties();


    @SuppressWarnings("WeakerAccess") // public for config
    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    @SuppressWarnings("WeakerAccess") // public for config
    public ResourceServerProperties getResource() {
        return resource;
    }

}