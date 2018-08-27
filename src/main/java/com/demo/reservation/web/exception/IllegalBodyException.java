package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalBodyException extends RuntimeException {

    private final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    private       String     message;

    public IllegalBodyException(Object id, Class type) {

        this.message = String.format("[%s] for [%s] is conflict. please check one more time.",
                type.getSimpleName(), id.toString());
    }

    public IllegalBodyException(String message) {

        this.message = message;
    }

    public IllegalBodyException(String message, String message1) {

        super(message);
        this.message = message1;
    }

    public IllegalBodyException(String message, Throwable cause, String message1) {

        super(message, cause);
        this.message = message1;
    }

    public IllegalBodyException(Throwable cause, String message) {

        super(cause);
        this.message = message;
    }

    public IllegalBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }
}
