package com.alexa.account_opening_service.service;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import jakarta.validation.Valid;


public interface CustomerAccountService {
    AccountResponseDTO beginAccountCreation(@Valid AccountRequestDTO requestDto);

    AccountResponseDTO updateAccountCreation(String requestId,
                                             @Valid AccountRequestDTO requestDto);

    AccountResponseDTO pauseAccountCreation(String requestId);

    AccountResponseDTO resumeAccountCreation(String requestId);

    AccountResponseDTO submitAccountCreationRequest(String requestId);

    AccountRequestDTO getAccountRequestById(String requestId);
}
