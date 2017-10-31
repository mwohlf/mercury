package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
public class UserAuthorizationRedirectBuilder {

    private final OAuthProviderConfig config;
    private String state;

    public UserAuthorizationRedirectBuilder(OAuthProviderConfig config) {
        this.config = config;
    }

    /*
     * user consent
     *
     * Redirecting to 'https://www.facebook.com/dialog/oauth
     *   ?client_id=1368363149952141
     *   &redirect_uri=http://localhost:8080/login/facebook
     *   &response_type=code
     *   &state=POvBmD'
     */
    public ResponseEntity build() {
        try {
            AuthorizationCodeResourceDetails client = config.getClient();
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                    .uri(new URI(client.getUserAuthorizationUri()))
                    // required, client id known by the provider
                    .queryParam("client_id", client.getClientId())
                    // required, redirect after the user confirmed, scheme, case and trailing slash must match
                    .queryParam("redirect_uri", client.getPreEstablishedRedirectUri())
                    // access scope, user must agree
                    // google scopes: https://developers.google.com/identity/protocols/googlescopes
                    // facebook scopes: https://developers.facebook.com/docs/facebook-login/permissions/
                    .queryParam("scope", String.join(" ", client.getScope()))
                    // generated key
                    .queryParam("state", this.state)
                    // google values: online/offline offline to trigger retrieving refresh token
                    .queryParam("access_type", "offline")
                    // required
                    .queryParam("response_type", "code")
                    // google optional values: none//select_account
                    .queryParam("prompt", "consent")
                    ;
            // redirect the client
            HttpHeaders headers = new HttpHeaders();
            String redirect = uriComponentsBuilder.build().toUriString();
            headers.add("Location", redirect);
            log.info("<build> redirecting to {}", redirect);
            return new ResponseEntity<String>(headers, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("URI is invalid", ex);
        }
    }

    public UserAuthorizationRedirectBuilder state(String state) {
        this.state = state;
        return this;
    }
}
