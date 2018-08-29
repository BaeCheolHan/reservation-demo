package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalException extends RuntimeException implements HttpServerException {

    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private       String     message;

    public InternalException(String message) {

        this.message = message;
    }
}
