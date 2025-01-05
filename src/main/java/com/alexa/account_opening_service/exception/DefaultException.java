package com.alexa.account_opening_service.exception;

import org.springframework.http.HttpStatus;

public record DefaultException(String errorMessage, HttpStatus httpStatus) {
}
