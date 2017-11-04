package net.wohlfart.mercury.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControlerExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = {
            RuntimeException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
    })
    protected ResponseEntity<Object> processException(RuntimeException ex, WebRequest request) {
        log.info("handling exception for ", request.getContextPath());
        request.getHeaderNames().forEachRemaining(key -> {
            log.info("  - '" + key + "': " + request.getHeader(key));
        });
        log.info(" parameters " + request.getParameterMap().size());
        request.getParameterNames().forEachRemaining(key -> {
            log.info("  - '" + key + "': " + request.getParameter(key));
        });
       // log.info(" sessionId " + request.getSessionId());
        log.info(" contextPath: " + request.getContextPath());
        log.info(" locale: " + request.getLocale());
        log.info(" remoteUser: " + request.getRemoteUser());
        log.info(" userPrincipal: " + request.getUserPrincipal());
        log.error(" stacktrace: ", ex);

        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}