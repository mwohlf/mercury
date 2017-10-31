package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static net.wohlfart.mercury.SecurityConstants.OAUTH_ENDPOINT;

@Slf4j
@Controller
@Configuration // for the bean annotation
public class OAuthController {

    private static final String STATE_KEY = "state";
    private static final String CODE_KEY = "code";

    private static final String GITHUB_PROVIDER = "github";
    private static final String FACEBOOK_PROVIDER = "facebook";
    private static final String GOOGLE_PROVIDER = "google";

    private static final HashMap<String, OAuthProviderConfig> providerConfigs = new HashMap<>();

    @PostConstruct
    public void setupProviders() {
        providerConfigs.put(GITHUB_PROVIDER, github());
        providerConfigs.put(FACEBOOK_PROVIDER, facebook());
        providerConfigs.put(GOOGLE_PROVIDER, google());
    }

    @GetMapping(OAUTH_ENDPOINT + "/{provider}")
    public ResponseEntity authenticate(@PathVariable("provider") String provider,
                                       HttpServletRequest request) throws AuthenticationException, URISyntaxException {
        log.info("<authenticate> provider '{}'", provider);

        final OAuthProviderConfig config = providerConfigs.get(provider);
        if (config == null) {
            return ResponseEntity.ok("unknown provider " + provider);
        }

        String state = request.getParameter(STATE_KEY);
        if (StringUtils.isEmpty(state)) {
            state = "AAA";
            return new UserAuthorizationRedirectBuilder(config).state(state).build();
        }

        final String code = request.getParameter(CODE_KEY);
        if (!StringUtils.isEmpty(code)) {
            HashMap response = new AccessTokenRetriever(config, code).request();
            log.info("<authenticate> {}", response);
            // return retrieveAccessToken(config, code);
        }


        return ResponseEntity.ok("todo");

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
