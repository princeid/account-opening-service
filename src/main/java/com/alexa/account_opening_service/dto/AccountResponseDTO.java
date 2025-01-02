package com.alexa.account_opening_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class AccountResponseDTO {
    private String requestId;
    private String status;
    private String message;
}
