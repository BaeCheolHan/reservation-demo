package com.demo.reservation.web.exception;

import org.springframework.http.HttpStatus;

public interface HttpServerException {

    HttpStatus getStatus();

    String getMessage();
}
