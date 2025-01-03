package com.alexa.account_opening_service.service.impl;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import com.alexa.account_opening_service.entity.State;
import com.alexa.account_opening_service.entity.Status;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.CustomerAccountService;
import com.alexa.account_opening_service.service.mapper.CustomerAccountMapper;
import com.alexa.account_opening_service.service.mapper.CustomerAccountResponseMapper;
import com.alexa.account_opening_service.service.util.Generator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public AccountResponseDTO beginAccountCreation(@Valid AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        AccountRequest accountRequest = CustomerAccountMapper.mapToEntity(requestDto);
        return CustomerAccountResponseMapper.mapToResponse(start(accountRequest));
    }

    @Override
    public AccountResponseDTO updateAccountCreation(@Valid AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        AccountRequest accountRequest = CustomerAccountMapper.mapToEntity(requestDto);
        return CustomerAccountResponseMapper.mapToResponse(update(accountRequest));
    }

    @Override
    public AccountResponseDTO pauseAccountCreation(@Valid String requestId) {
        Objects.requireNonNull(requestId);
        AccountRequest accountRequest = customerAccountRepository.findByRequestId(requestId);
        return CustomerAccountResponseMapper.mapToResponse(pause(accountRequest));
    }

    @Override
    public AccountResponseDTO submitAccountCreationRequest(@Valid AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        AccountRequest accountRequest = CustomerAccountMapper.mapToEntity(requestDto);
        return CustomerAccountResponseMapper.mapToResponse(complete(accountRequest));
    }

    @Override
    public AccountResponseDTO getAccountRequestById(String requestId) {
        Objects.requireNonNull(requestId);
        return CustomerAccountResponseMapper.mapToResponse(customerAccountRepository.findByRequestId(requestId));
    }

    private AccountRequest start(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withRequestId(Generator.generateRequestId())
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .withState(State.STARTED)
                .withCreationDateTime(Instant.now())
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation initiated - request: {}", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest pause(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .withState(State.PAUSED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation paused - current request: {}", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest update(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.PENDING)
                .withMessage(State.UPDATED.getMessage())
                .withState(State.UPDATED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation updated with additional info - request: {}", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest complete(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .withState(State.COMPLETED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation completed - request: {}", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest save(final AccountRequest accountRequest) {
        return customerAccountRepository.save(accountRequest);
    }
}
