package com.alexa.account_opening_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountOpeningExceptionHandler {
    @ExceptionHandler(value = IdNotFoundException.class)
    public ResponseEntity<Object> handleRequestIdNotFoundException(IdNotFoundException idNotFoundException) {
        DefaultException defaultException = new DefaultException(
                idNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(defaultException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException) {
        DefaultException defaultException = new DefaultException(
                badRequestException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(defaultException, HttpStatus.BAD_REQUEST);
    }
}
