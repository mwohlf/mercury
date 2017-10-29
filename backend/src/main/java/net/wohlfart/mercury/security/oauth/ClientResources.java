package net.wohlfart.mercury.security.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


// @Data
// @Configuration
@ConfigurationProperties
public class ClientResources {


  @NestedConfigurationProperty
  public AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();


  @NestedConfigurationProperty
  public ResourceServerProperties resource = new ResourceServerProperties();

  public AuthorizationCodeResourceDetails getClient() {
    return client;
  }

  public void setClient(AuthorizationCodeResourceDetails client) {
    this.client = client;
  }

  public ResourceServerProperties getResource() {
    return resource;
  }

  public void setResource(ResourceServerProperties resource) {
    this.resource = resource;
  }

}