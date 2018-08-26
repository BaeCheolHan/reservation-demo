package com.demo.reservation.web.global;

import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity handle204(NoContentException e) {

        return new ResponseEntity(e.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handle404(NotFoundException e) {

        return new ResponseEntity(e.getStatus());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity handle409(ConflictException e) {

        return new ResponseEntity(e.getStatus());
    }
}
