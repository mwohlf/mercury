package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * read the code token form the servlet request and put it into the  access token request
 * so it can be detected and used by the oauth template
 */
@Slf4j
class AuthFilter extends OAuth2ClientAuthenticationProcessingFilter {

    public static final String CODE_KEY = "code";
    private final DefaultOAuth2ClientContext defaultOAuth2ClientContext;

    public AuthFilter(String defaultFilterProcessesUrl, DefaultOAuth2ClientContext defaultOAuth2ClientContext) {
        super(defaultFilterProcessesUrl);
        this.defaultOAuth2ClientContext = defaultOAuth2ClientContext;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String authCode = request.getParameter(CODE_KEY);
        if (!StringUtils.isEmpty(authCode)) {
            log.debug("<attemptAuthentication> found code: " + authCode);
            defaultOAuth2ClientContext.getAccessTokenRequest().setAuthorizationCode(authCode);
        }
        return super.attemptAuthentication(request, response);
    }

}
