package com.alexa.account_opening_service.exception;

public class UniqueConstraintViolationException extends RuntimeException {
    public UniqueConstraintViolationException(final String message) {
        super(message);
    }
}
