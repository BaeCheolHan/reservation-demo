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
}
