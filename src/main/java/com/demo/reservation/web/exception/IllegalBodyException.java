package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalBodyException extends RuntimeException implements HttpServerException {

    private final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    private       String     message;

    public IllegalBodyException(String message) {

        this.message = message;
    }
}
