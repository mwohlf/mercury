package net.wohlfart.mercury.security.oauth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class AccessTokenRetriever {

    private final OAuthProviderConfig config;
    private final String code;

    public AccessTokenRetriever(OAuthProviderConfig config, String code) {
        this.config = config;
        this.code = code;
    }

    /*
     * Encoding and sending form: {
     *    grant_type=[authorization_code],
     *    code=[-gsdgsdgsdg-mnFWIYnAs875OrMt1nIjma8lRRC-syUreEgCxhV-EAHRSH3-KIQmTBW1W0E60MRI-ijuggwYjSrfaxXxijz1rUK9NyjzSK_17yQ9uVJDWcgx],
     *    redirect_uri=[http://localhost:8080/login/facebook],
     *    client_id=[1368363149952141],
     *    client_secret=[dfgsergsergseg]}
     */
    public HashMap request() {
        AuthorizationCodeResourceDetails client = this.config.getClient();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", client.getClientId());
        form.add("client_secret", client.getClientSecret());
        form.add("redirect_uri", client.getPreEstablishedRedirectUri());  // TODO: needed?
        form.add("code", this.code);
        form.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> result = restTemplate.postForEntity(client.getAccessTokenUri(), request, HashMap.class);
        return result.getBody();
    }

}
