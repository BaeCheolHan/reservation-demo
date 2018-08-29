package com.demo.reservation.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException implements HttpServerException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private       String     message;

    public NotFoundException(Object id, Class type) {

        this.message = String.format("[%s] for [%s] is not exist in DB. please check one more time.",
                type.getSimpleName(), id.toString());
    }
}
