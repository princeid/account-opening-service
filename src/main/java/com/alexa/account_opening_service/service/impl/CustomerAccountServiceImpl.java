package com.alexa.account_opening_service.service.impl;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.CustomerAccountService;
import com.alexa.account_opening_service.service.mapper.CustomerAccountMapper;
import com.alexa.account_opening_service.service.mapper.CustomerAccountResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;
    private final CustomerAccountMapper customerAccountMapper;
    private final CustomerAccountResponseMapper customerAccountResponseMapper;

    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository,
                                      CustomerAccountMapper customerAccountMapper, CustomerAccountResponseMapper customerAccountResponseMapper) {
        this.customerAccountRepository = customerAccountRepository;
        this.customerAccountMapper = customerAccountMapper;
        this.customerAccountResponseMapper = customerAccountResponseMapper;
    }

    @Override
    public AccountResponseDTO beginAccountCreation(@Valid AccountRequestDTO accountRequestDto) {
        Objects.requireNonNull(accountRequestDto);
        AccountRequest accountRequest = customerAccountMapper.toEntity(accountRequestDto);
        return customerAccountResponseMapper.toResponse(customerAccountRepository.save(accountRequest));
    }

    @Override
    public AccountResponseDTO updateAccountCreation(String requestId,
                                                    @Valid AccountRequestDTO requestDto) {
        return null;
    }

    @Override
    public AccountResponseDTO pauseAccountCreation(String requestId) {
        // Validate and throw exception
        return null;
    }

    @Override
    public AccountResponseDTO resumeAccountCreation(String requestId) {
        // Validate and throw exception
        return null;
    }

    @Override
    public AccountResponseDTO submitAccountCreationRequest(String requestId) {
        // Validate and throw exception
        return null;
    }

    @Override
    public AccountRequestDTO getAccountRequestById(String requestId) {
        return null;
    }
}
