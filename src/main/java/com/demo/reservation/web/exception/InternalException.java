package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalException extends Exception {

    private final HttpStatus status = HttpStatus.CONFLICT;
    private       String     message;

    public InternalException(Object id, Class type) {

        this.message = String.format("[%s] for [%s] is conflict. please check one more time.",
                type.getSimpleName(), id.toString());
    }

    public InternalException(String message) {

        this.message = message;
    }

    public InternalException(String message, String message1) {

        super(message);
        this.message = message1;
    }

    public InternalException(String message, Throwable cause, String message1) {

        super(message, cause);
        this.message = message1;
    }

    public InternalException(Throwable cause, String message) {

        super(cause);
        this.message = message;
    }

    public InternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {

        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }
}
