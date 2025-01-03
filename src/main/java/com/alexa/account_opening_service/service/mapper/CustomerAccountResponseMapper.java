package com.alexa.account_opening_service.service.mapper;

import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import jakarta.validation.Valid;

public class CustomerAccountResponseMapper {

    private CustomerAccountResponseMapper() {
        // static access only
    }

    public static AccountResponseDTO mapToResponse(@Valid final AccountRequest accountRequest) {
        return AccountResponseDTO.builder()
                .withId(accountRequest.getId() != null ? accountRequest.getId() : null)
                .withRequestId(accountRequest.getRequestId() != null ? accountRequest.getRequestId() : null)
                .withStatus(accountRequest.getStatus() != null ? accountRequest.getStatus() : null)
                .withState(accountRequest.getState() != null ? accountRequest.getState() : null)
                .withMessage(accountRequest.getMessage() != null ? accountRequest.getMessage() : null)
                .build();
    }
}
