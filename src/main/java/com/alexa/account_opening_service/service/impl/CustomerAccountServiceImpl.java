package com.alexa.account_opening_service.service.impl;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import com.alexa.account_opening_service.entity.Address;
import com.alexa.account_opening_service.entity.State;
import com.alexa.account_opening_service.entity.Status;
import com.alexa.account_opening_service.exception.BadRequestException;
import com.alexa.account_opening_service.exception.IdNotFoundException;
import com.alexa.account_opening_service.exception.UniqueConstraintViolationException;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.CustomerAccountService;
import com.alexa.account_opening_service.service.mapper.CustomerAccountResponseMapper;
import com.alexa.account_opening_service.service.util.Generator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

import static com.alexa.account_opening_service.service.mapper.CustomerAccountMapper.mapToEntity;
import static com.alexa.account_opening_service.service.mapper.CustomerAccountMapper.mapUpdateToEntity;
import static com.alexa.account_opening_service.service.mapper.CustomerAccountResponseMapper.mapToResponse;

@Service
@Transactional
@Slf4j
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountServiceImpl(final CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public AccountResponseDTO beginAccountCreation(@Valid final AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        final AccountRequest accountRequest = mapToEntity(requestDto);
        return mapToResponse(start(accountRequest));
    }

    @Override
    public AccountResponseDTO updateAccountCreation(@Valid final AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        final var request = customerAccountRepository.findById(requestDto.getId());

        return request.map(accountRequest -> {
            if (!Objects.equals(accountRequest.getRequestId(), requestDto.getRequestId())) {
                throw new BadRequestException("Invalid requestId " + requestDto.getRequestId());
            }
            accountRequest = mapUpdateToEntity(accountRequest,
                    requestDto.toBuilder()
                            .withAddress(updateAddress(accountRequest.getAddress(), requestDto.getAddress()))
                            .build()); // Update address entity instead of creating new
            return mapToResponse(update(accountRequest));
        }).orElseThrow(() -> new IdNotFoundException("Account not found with id " + requestDto.getId()));
    }

    @Override
    public AccountResponseDTO pauseAccountCreation(final String requestId) {
        Objects.requireNonNull(requestId);
        final var accountRequest = customerAccountRepository.findByRequestId(requestId);
        return accountRequest
                .map(request -> {
                    final var pausedAccountRequest = pause(request);
                    return mapToResponse(pausedAccountRequest);
                })
                .orElseThrow(() -> new IdNotFoundException("Account not found with requestId " + requestId));
    }

    @Override
    public AccountResponseDTO submitAccountCreationRequest(@Valid final AccountRequestDTO requestDto) {
        Objects.requireNonNull(requestDto);
        final var request = customerAccountRepository.findById(requestDto.getId());

        return request.map(accountRequest -> {
            if (!Objects.equals(accountRequest.getRequestId(), requestDto.getRequestId())) {
                throw new BadRequestException("Invalid requestId " + requestDto.getRequestId());
            }
            accountRequest = mapUpdateToEntity(accountRequest,
                    requestDto.toBuilder()
                            .withAddress(updateAddress(accountRequest.getAddress(), requestDto.getAddress()))
                            .build()); // Update address entity instead of creating new
            return mapToResponse(submit(accountRequest));
        }).orElseThrow(() -> new IdNotFoundException("Account not found with id " + requestDto.getId()));
    }

    @Override
    public AccountResponseDTO getAccountRequestById(final String requestId) {
        Objects.requireNonNull(requestId);
        final var request = customerAccountRepository.findByRequestId(requestId);
        return request.map(CustomerAccountResponseMapper::mapToResponse).orElseThrow(() ->
                new IdNotFoundException("Account not found with requestId " + requestId));

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
        log.info("Account creation initiated - request: {} ", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest pause(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .withState(State.PAUSED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation paused - current request: {} ", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest update(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.PENDING)
                .withMessage(State.UPDATED.getMessage())
                .withState(State.UPDATED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation updated with additional info request: {} ", accountRequest);
        return save(accountRequest);
    }

    private AccountRequest submit(AccountRequest accountRequest) {
        accountRequest = accountRequest.toBuilder()
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .withState(State.COMPLETED)
                .withUpdateDateTime(Instant.now())
                .build();
        log.info("Account creation completed - request: {}", accountRequest);
        return save(accountRequest);
    }

    private Address updateAddress(final Address oldAddress, final Address newAddress) {
        return newAddress.toBuilder().withId(oldAddress.getId()).build();
    }

    private AccountRequest save(final AccountRequest accountRequest) {
        // Check if the request has already been completed before updating
        if (accountRequest.getId() != null) {
            final var currentRequest = customerAccountRepository.findById(accountRequest.getId());
            currentRequest.ifPresentOrElse(
                    request -> {
                        if (request.getStatus().equals(Status.CONFIRMED)) {
                            throw new BadRequestException("Cannot update request. " +
                                    "Account opening request is already completed for requestId "
                                    + request.getRequestId());
                        }
                    },
                    () -> log.info("Saving account request for requestId {}", accountRequest.getRequestId())
            );
        }

        try {
            return customerAccountRepository.save(accountRequest);
        } catch (final DataIntegrityViolationException ex) {
            final Throwable rootCause = ex.getRootCause();
            if (rootCause != null && rootCause.getMessage().contains("Duplicate entry")) {
                throw new UniqueConstraintViolationException("Email already exists: " + accountRequest.getEmail());
            }
            throw ex; // Re-throw for other integrity violations
        }
    }
}
