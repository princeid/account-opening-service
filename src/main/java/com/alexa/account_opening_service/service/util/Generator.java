package com.alexa.account_opening_service.service.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class Generator {

    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}
