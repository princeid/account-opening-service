package com.alexa.account_opening_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.alexa.account_opening_service")
public class AccountOpeningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountOpeningServiceApplication.class, args);
    }
}
