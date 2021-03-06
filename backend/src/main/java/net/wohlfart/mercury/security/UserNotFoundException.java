package net.wohlfart.mercury.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Subject not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Exception cause) {
        super(cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
