package com.alexa.account_opening_service.service;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import jakarta.validation.Valid;


public interface CustomerAccountService {
    AccountResponseDTO beginAccountCreation(@Valid final AccountRequestDTO requestDto);

    AccountResponseDTO updateAccountCreation(@Valid final AccountRequestDTO requestDto);

    AccountResponseDTO pauseAccountCreation(final String requestId);

    AccountResponseDTO submitAccountCreationRequest(@Valid final AccountRequestDTO requestDto);

    AccountResponseDTO getAccountRequestById(final String requestId);
}
