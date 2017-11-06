package net.wohlfart.mercury.configuration;

import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.model.AlertMessage;
import net.wohlfart.mercury.security.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(value = {
            RuntimeException.class,
    })
    protected ResponseEntity<AlertMessage> processException(RuntimeException ex, WebRequest request) {
        log.info("<processException> " + ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        HttpHeaders headers = new HttpHeaders();
        String message = "bad request";
        String details = createRequestDetails(ex, request);

        if (ex instanceof AccessDeniedException) {
            status = HttpStatus.UNAUTHORIZED;
            message = "access denied";
        } else if (ex instanceof UserNotFoundException) {
            status = HttpStatus.UNAUTHORIZED;
            message = "user not found";
        }
        log.info(details);
        return new ResponseEntity<>(new AlertMessage(status.value(), message, details), headers, status);
    }

    // should we do json here?
    private String createRequestDetails(RuntimeException ex, WebRequest request) {
        final ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(content);
        printWriter.println("handling exception for '" + request.getContextPath() + "'");
        printWriter.println("  headers:");
        request.getHeaderNames().forEachRemaining(key -> {
            printWriter.println("   - '" + key + "': '" + request.getHeader(key) + "'");
        });
        printWriter.println("  parameters:");
        request.getParameterNames().forEachRemaining(key -> {
            printWriter.println("   - '" + key + "': '" + request.getParameter(key) + "'");
        });
        printWriter.println("  contextPath: " + request.getContextPath());
        printWriter.println("  locale: " + request.getLocale());
        printWriter.println("  remoteUser: " + request.getRemoteUser());
        printWriter.println("  userPrincipal: " + request.getUserPrincipal());
        printWriter.println("  exception: " + ex.getClass());
        printWriter.println("    message: " + ex.getMessage());
        printWriter.println("    cause: " + ex.getCause());
        printWriter.println("  stacktrace: ");
        ex.printStackTrace(printWriter);
        printWriter.flush();
        return new String(content.toByteArray(), Charset.forName("UTF-8"));
    }

}