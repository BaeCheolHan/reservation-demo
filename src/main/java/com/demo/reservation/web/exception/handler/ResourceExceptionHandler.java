package com.demo.reservation.web.exception.handler;

import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.HttpServerException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.demo.reservation.web.resource")
public class ResourceExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = { ConflictException.class, IllegalBodyException.class, NoContentException.class, NotFoundException.class, InternalException.class })
    public ResponseEntity<String> handleKnownExceptions(HttpServerException e) {

        logger.warn("[{}] -> return [{}]", e.getMessage(), e.getStatus());

        return new ResponseEntity<>("An unrecognized error occurred.", e.getStatus());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<String> handleUnknownExceptions(Exception e) {

        logger.error("An unrecognized error occurred.", e);

        return new ResponseEntity<>("An unrecognized error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
