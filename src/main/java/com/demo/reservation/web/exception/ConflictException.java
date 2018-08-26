package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends RuntimeException {

    private final HttpStatus status = HttpStatus.CONFLICT;
    private String message;

    public ConflictException(String message) {
        this.message = message;
    }

    public ConflictException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public ConflictException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public ConflictException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }
}
