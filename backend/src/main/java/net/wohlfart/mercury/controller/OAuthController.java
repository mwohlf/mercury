package net.wohlfart.mercury.controller;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.User;
import net.wohlfart.mercury.security.JwtTokenUtil;
import net.wohlfart.mercury.security.UserDetailsImpl;
import net.wohlfart.mercury.security.oauth.*;
import net.wohlfart.mercury.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private static final HashMap<String, OAuthProviderConfig> PROVIDER_CONFIG = new HashMap<>();

    private static final StateManager STATE_MANAGER = new StateManager();

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostConstruct
    public void setupProviders() {
        PROVIDER_CONFIG.put(GITHUB_PROVIDER, github());
        PROVIDER_CONFIG.put(FACEBOOK_PROVIDER, facebook());
        PROVIDER_CONFIG.put(GOOGLE_PROVIDER, google());
    }

    @GetMapping(OAUTH_ENDPOINT + "/{provider}")
    public ResponseEntity authenticate(@PathVariable("provider") String provider,
                                       HttpServletRequest request) throws AuthenticationException, URISyntaxException, IOException {
        log.info("<authenticate> provider '{}'", provider);

        final OAuthProviderConfig config = PROVIDER_CONFIG.get(provider);
        if (config == null) {
            return ResponseEntity.ok("unknown provider " + provider);
        }

        String stateId = request.getParameter(STATE_KEY);
        if (StringUtils.isEmpty(stateId)) {
            stateId = STATE_MANAGER.createState().getKey();
            return new AuthRedirectBuilder(config).state(stateId).build();   // redirect to provider
        }

        if (!STATE_MANAGER.hasState(stateId)) {
            // TODO: maybe store the ip in the state and check here
            return ResponseEntity.ok("unknown state '" + stateId + "' for provider '" + provider + "'");
        }

        final DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource indexFile = loader.getResource("classpath:public/index.html");
        String responseContent = CharStreams.toString(new InputStreamReader(indexFile.getInputStream(), Charsets.UTF_8));

        final String code = request.getParameter(CODE_KEY);
        if (!StringUtils.isEmpty(code)) {
            ResponseEntity<HashMap> tokenResponse =  new AccessTokenRetriever(config, code).request();  // request provider
            log.info("<authenticate> {}", tokenResponse);
            log.info("<authenticate> body: {}", tokenResponse.getBody());
            String accessToken = (String) tokenResponse.getBody().get("access_token");
            ResponseEntity<HashMap> userResponse = new UserDataRetriever(config, accessToken).request();
            log.info("<authenticate> userResponse: {}", userResponse.getBody());
            User user = accountFactory.findOrCreate(provider, tokenResponse.getBody(), userResponse.getBody());
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserById(user.getId());

            String jwtToken = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity
                    .ok()
                    .headers(jwtTokenUtil.cookies(jwtToken))
                    .contentLength(responseContent.length())
                    .contentType(MediaType.TEXT_HTML)
                    .body(responseContent);
        } else {
            return ResponseEntity
                    .ok()
                    .headers(jwtTokenUtil.cookies(""))
                    .contentLength(responseContent.length())
                    .contentType(MediaType.TEXT_HTML)
                    .body(responseContent);
        }
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
