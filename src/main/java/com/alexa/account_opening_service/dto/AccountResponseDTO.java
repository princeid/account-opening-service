package com.alexa.account_opening_service.dto;

import com.alexa.account_opening_service.entity.State;
import com.alexa.account_opening_service.entity.Status;
import lombok.Builder;

@Builder(toBuilder = true, setterPrefix = "with")
public record AccountResponseDTO(Long id, String requestId, Status status, State state, String message) {
}
