package com.demo.reservation.web.exception.handler;

import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.HttpServerException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.demo.reservation.web.controller")
public class ViewExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = { ConflictException.class, IllegalBodyException.class, NoContentException.class, NotFoundException.class, InternalException.class })
    public String handleKnownExceptions(HttpServerException e) {

        logger.warn("[{}] -> will return error page.", e.getMessage());
        return "error";
    }

    @ExceptionHandler(value = { Exception.class })
    public String handleUnknownExceptions(Exception e) {

        logger.error("unrecognized error occurred.", e);
        return "error";
    }
}
