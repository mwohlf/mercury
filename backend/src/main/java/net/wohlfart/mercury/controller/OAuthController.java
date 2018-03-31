package net.wohlfart.mercury.controller;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.OAuthProviderInfo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import static net.wohlfart.mercury.SecurityConstants.OAUTH_ENDPOINT;

@Slf4j
@Controller
@Configuration // for the bean annotation
public class OAuthController {

    private static final String STATE_KEY = "state";

    private static final String GITHUB_PROVIDER = "github";
    private static final String FACEBOOK_PROVIDER = "facebook";
    private static final String GOOGLE_PROVIDER = "google";
    private static final String TWITTER_PROVIDER = "twitter";

    private static final HashMap<String, OAuthProviderConfig> PROVIDER_CONFIGS = new HashMap<>();

    private static final List<OAuthProviderInfo> PROVIDER_INFO = new ArrayList<>();

    private static final StateManager STATE_MANAGER = new StateManager();

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostConstruct
    public void setupProviders() {
        PROVIDER_CONFIGS.put(GITHUB_PROVIDER, github());
        PROVIDER_CONFIGS.put(FACEBOOK_PROVIDER, facebook());
        PROVIDER_CONFIGS.put(GOOGLE_PROVIDER, google());
        PROVIDER_CONFIGS.put(TWITTER_PROVIDER, google());
        // infor for the UI
        PROVIDER_CONFIGS.forEach(
            (key, oAuthProviderConfig) -> PROVIDER_INFO.add(new OAuthProviderInfo(key, key))
        );
    }

    @GetMapping(OAUTH_ENDPOINT)
    public ResponseEntity<List<OAuthProviderInfo>> getProvider() {
        return ResponseEntity.ok().body(PROVIDER_INFO);
    }

    @GetMapping(OAUTH_ENDPOINT + "/{provider}")
    public ResponseEntity authenticate(@PathVariable("provider") String provider,
                                       HttpServletRequest request) throws AuthenticationException, URISyntaxException, IOException {
        log.info("<authenticate> provider '{}' detected", provider);

        final OAuthProviderConfig authProvider = PROVIDER_CONFIGS.get(provider);
        if (authProvider == null) {
            return ResponseEntity.ok("unknown provider '" + provider + "'"
                + " available providers are " + Arrays.toString(PROVIDER_CONFIGS.keySet().toArray()));
        }

        String stateId = request.getParameter(STATE_KEY);
        if (StringUtils.isEmpty(stateId)) {
            stateId = STATE_MANAGER.createState().getKey();
            return redirectForAuthentication(authProvider, stateId);
        }

        // returning from provider, there is a stateId
        if (!STATE_MANAGER.hasState(stateId)) {
            // TODO: store the ip and or nounce (in the state) and check here
            // this happens when reusing or replaying the url from the redirect
            return ResponseEntity.ok("unknown state '" + stateId + "' for authProvider '" + authProvider + "'");
        }
        final AuthContext authContext =  new AuthContext();
        authContext.useProvider(provider);

        final String code = authContext.collectRedirectParameters(request.getParameterMap()).getCode();
        if (StringUtils.isEmpty(code)) {
            log.error("no code found after redirect, back to start page");
            return startPage();
        }

        final String accessToken = authContext.collectAccessTokenParameters(requestAccessToken(authProvider, authContext)).getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            log.error("no accessToken found after request");
            return startPage();
        }
        HashMap userProfile = requestUserProfile(authProvider, authContext);

        User user = accountFactory.findOrCreate(provider, userProfile);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserById(user.getId());

        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        final String startPage = getStartPage();
        return ResponseEntity
            .ok()
            .headers(jwtTokenUtil.cookies(jwtToken))
            .contentLength(startPage.length())
            .contentType(MediaType.TEXT_HTML)
            .body(startPage);

    }

    private ResponseEntity startPage() throws IOException {
        final String startPage = getStartPage();
        return ResponseEntity
            .ok()
            .headers(jwtTokenUtil.cookies(""))
            .contentLength(startPage.length())
            .contentType(MediaType.TEXT_HTML)
            .body(startPage);
    }

    private String getStartPage() throws IOException {
        final DefaultResourceLoader loader = new DefaultResourceLoader();
        final Resource indexFile = loader.getResource("classpath:public/index.html");
        return CharStreams.toString(new InputStreamReader(indexFile.getInputStream(), Charsets.UTF_8));
    }

    private ResponseEntity redirectForAuthentication(OAuthProviderConfig config, String stateId) {
        return new AuthRedirectBuilder(config.getClient()).state(stateId).build();   // redirect to provider
    }

    private HashMap requestAccessToken(OAuthProviderConfig config, AuthContext authContext) {
        assert authContext.getCode() != null : "code must not be null";
        final ResponseEntity<HashMap> accessTokenResponse =  new AccessTokenRetriever(config.getClient()).code(authContext.getCode()).request();
        log.info("<requestAccessToken> {}", accessTokenResponse);
        log.info("<requestAccessToken> body: {}", accessTokenResponse.getBody());
        return accessTokenResponse.getBody();
    }

    private HashMap requestUserProfile(OAuthProviderConfig config, AuthContext authContext) {
        assert authContext.getAccessToken() != null : "accessToken must not be null";
        final ResponseEntity<HashMap> userProfileResponse = new UserDataRetriever(config).accessToken(authContext.getAccessToken()).request();
        log.info("<requestUserProfile>: {}", userProfileResponse);
        log.info("<requestUserProfile> body: {}", userProfileResponse.getBody());
        return userProfileResponse.getBody();
    }

    // implementing strategies for different auth provider here

    @ConfigurationProperties(prefix=GITHUB_PROVIDER, ignoreUnknownFields = false)
    public OAuthProviderConfig github() {
        return new OAuthProviderConfig();
    }

    @ConfigurationProperties(prefix=FACEBOOK_PROVIDER, ignoreUnknownFields = false)
    public OAuthProviderConfig facebook() {
        return new OAuthProviderConfig();
    }

    @ConfigurationProperties(prefix=GOOGLE_PROVIDER, ignoreUnknownFields = false)
    public OAuthProviderConfig google() {
        return new OAuthProviderConfig();
    }

    @ConfigurationProperties(prefix=TWITTER_PROVIDER, ignoreUnknownFields = false)
    public OAuthProviderConfig twitter() {
        return new OAuthProviderConfig();
    }

}
