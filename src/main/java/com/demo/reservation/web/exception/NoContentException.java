package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoContentException extends Exception implements HttpServerException {

    private final HttpStatus status = HttpStatus.NO_CONTENT;
    private       String     message;

    public NoContentException(String message) {

        this.message = message;
    }

    public NoContentException(String message, String message1) {

        super(message);
        this.message = message1;
    }

    public NoContentException(String message, Throwable cause, String message1) {

        super(message, cause);
        this.message = message1;
    }

    public NoContentException(Throwable cause, String message) {

        super(cause);
        this.message = message;
    }

}
