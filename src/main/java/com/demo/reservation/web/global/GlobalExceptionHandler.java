package com.demo.reservation.web.global;

import com.demo.reservation.web.exception.ConflictException;
import com.demo.reservation.web.exception.IllegalBodyException;
import com.demo.reservation.web.exception.InternalException;
import com.demo.reservation.web.exception.NoContentException;
import com.demo.reservation.web.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> handle204(NoContentException e) {

        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle404(NotFoundException e) {

        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handle409(ConflictException e) {

        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(IllegalBodyException.class)
    public ResponseEntity<String> handle422(IllegalBodyException e) {

        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<String> handle500(InternalException e) {

        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
