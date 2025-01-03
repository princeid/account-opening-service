package com.alexa.account_opening_service.service;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import jakarta.validation.Valid;


public interface CustomerAccountService {
    AccountResponseDTO beginAccountCreation(@Valid AccountRequestDTO requestDto);

    AccountResponseDTO updateAccountCreation(@Valid AccountRequestDTO requestDto);

    AccountResponseDTO pauseAccountCreation(String requestId);

    AccountResponseDTO submitAccountCreationRequest(@Valid AccountRequestDTO requestDto);

    AccountResponseDTO getAccountRequestById(String requestId);
}
