package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private String message;

    public NotFoundException(Object id, Class type) {
        this.message = String.format("[%s] for [%s] is not exist in DB. please check one more.",
                type.getSimpleName(), id.toString());
    }

    public NotFoundException(String message) {
        this.message = message;
    }

    public NotFoundException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public NotFoundException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public NotFoundException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }
}
