package net.wohlfart.mercury.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

@Slf4j
public class ProviderResponseParser {

    private static final HttpMessageConverter[] CONVERTERS = {
            new FormHttpMessageConverter(),
    };


    public void parse(ResponseEntity<String> response) {
        HttpStatus statusCode = response.getStatusCode();
        log.info("<parse> statusCode: {}", statusCode);

        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        log.info("<parse> contentType: {}", contentType);



    }

}
