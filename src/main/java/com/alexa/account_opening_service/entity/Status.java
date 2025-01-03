package com.alexa.account_opening_service.entity;

public enum Status {
    PENDING("Request created. You can resume anytime."), // PAUSED or UPDATED State
    CONFIRMED("Account creation completed successfully."); // COMPLETED State

    private final String message;

    Status(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}