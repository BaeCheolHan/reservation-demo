package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends RuntimeException implements HttpServerException {

    private final HttpStatus status = HttpStatus.CONFLICT;
    private       String     message;

    public ConflictException(Object id, Class type) {

        this.message = String.format("[%s] for [%s] is conflict. please check one more time.",
                type.getSimpleName(), id.toString());
    }
}
