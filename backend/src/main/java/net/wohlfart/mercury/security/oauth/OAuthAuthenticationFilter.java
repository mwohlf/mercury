package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OAuthAuthenticationFilter extends OAuth2ClientAuthenticationProcessingFilter {

    public OAuthAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("<attemptAuthentication> " + request);

        String authCode = request.getParameter("code");
        if (!StringUtils.isEmpty(authCode)) {
            log.info("<attemptAuthentication> code: " + authCode);
        }

        return super.attemptAuthentication(request, response);
    }

}
