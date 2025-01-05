package com.alexa.account_opening_service.entity;

import lombok.Getter;

@Getter
public enum State {
    STARTED("Account opening request started."), // Status is PENDING
    PAUSED("Request paused. Resume anytime."), // Status is PENDING
    UPDATED("Details updated. Request is still pending confirmation."), // Status is PENDING
    COMPLETED("Request completed."); // Status is CONFIRMED

    private final String message;

    State(final String message) {
        this.message = message;
    }

}
