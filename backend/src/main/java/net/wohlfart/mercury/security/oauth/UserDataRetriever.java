package net.wohlfart.mercury.security.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class UserDataRetriever {

    private final OAuthProviderConfig config;
    private final String accessToken;

    public UserDataRetriever(OAuthProviderConfig config, String accessToken) {
        this.config = config;
        this.accessToken = accessToken;
    }

    public ResponseEntity<HashMap> request() {
        ResourceServerProperties resource = this.config.getResource();
        try {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                    .uri(new URI(resource.getUserInfoUri()))
                    .queryParam("access_token", this.accessToken)
                    ;
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForEntity(uriComponentsBuilder.build().toUriString(), HashMap.class);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URI is invalid", ex);
        }
    }

}
