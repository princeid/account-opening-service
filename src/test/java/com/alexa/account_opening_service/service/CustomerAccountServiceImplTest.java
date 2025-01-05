package com.alexa.account_opening_service.service;


import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.*;
import com.alexa.account_opening_service.exception.BadRequestException;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.impl.CustomerAccountServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerAccountServiceImplTest {
    private static final Long ID = 1L;
    private static final String REQUEST_ID = "random-uuid-request-id-1";

    private final CustomerAccountRepository customerAccountRepository = mock(CustomerAccountRepository.class);

    @InjectMocks
    private CustomerAccountServiceImpl serviceImplMock;

    private AccountRequestDTO accountRequestDTO;
    private AccountRequest accountRequest;

    @BeforeEach
    public void setup() {
        // Setup mock data
        accountRequestDTO = AccountRequestDTO.builder()
                .withId(ID)
                .withName("Alexa")
                .withAddress(Address.builder()
                        .withHouseNumber("1")
                        .withStreet("Cornerstone Street")
                        .withCity("Amsterdam")
                        .withPostalCode("1234 AB")
                        .build())
                .withDateOfBirth("1990-10-05")
                .withIdDocument("http//example.com.doc-01.pdf")
                .withAccountType(AccountType.SAVINGS)
                .build();

        accountRequest = AccountRequest.builder()
                .withId(ID)
                .withName("Alexa")
                .withAddress(Address.builder()
                        .withHouseNumber("1")
                        .withStreet("Cornerstone Street")
                        .withCity("Amsterdam")
                        .withPostalCode("1234 AB")
                        .build())
                .withDateOfBirth("1990-10-05")
                .withIdDocument("http//example.com.doc-01.pdf")
                .withAccountType(AccountType.SAVINGS)
                .build();
    }

    @AfterEach
    void tearDown() {
        accountRequestDTO = null;
        accountRequest = null;
    }

    @Test
    void shouldBeginAccountCreation() {
        // Given
        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final var response = serviceImplMock.beginAccountCreation(accountRequestDTO);
        final var accountResponseDTO = AccountResponseDTO.builder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();

        // Then
        verify(customerAccountRepository).save(any(AccountRequest.class));
        assertThat(response).isEqualTo(accountResponseDTO);
    }

    @Test
    void shouldUpdateAccountCreation() {
        // Given
        accountRequestDTO = accountRequestDTO.toBuilder().withRequestId(REQUEST_ID).build();
        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.UPDATED)
                .withStatus(Status.PENDING)
                .withMessage(State.UPDATED.getMessage())
                .build();
        when(customerAccountRepository.findById(ID)).thenReturn(Optional.ofNullable(expected));
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final var response = serviceImplMock.updateAccountCreation(accountRequestDTO.toBuilder()
                .withEmail("example@test.com")
                .withStartingBalance(2000.05D)
                .build());
        final var accountResponseDTO = AccountResponseDTO.builder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.UPDATED)
                .withStatus(Status.PENDING)
                .withMessage(State.UPDATED.getMessage())
                .build();

        // Then
        verify(customerAccountRepository).save(any(AccountRequest.class));
        assertThat(response).isEqualTo(accountResponseDTO);
    }

    @Test
    void shouldPauseAccountCreation() {
        // Given
        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.PAUSED)
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .build();
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(Optional.ofNullable(expected));
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final var response = serviceImplMock.pauseAccountCreation(REQUEST_ID);
        final var accountResponseDTO = AccountResponseDTO.builder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.PAUSED)
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .build();

        // Then
        verify(customerAccountRepository).save(any(AccountRequest.class));
        assertThat(response).isEqualTo(accountResponseDTO);
    }

    @Test
    void shouldCompleteAccountCreation() {
        // Given
        accountRequestDTO = accountRequestDTO.toBuilder().withRequestId(REQUEST_ID).build();
        final var current = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.PAUSED)
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .build();

        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.COMPLETED)
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .build();
        when(customerAccountRepository.findById(ID)).thenReturn(Optional.ofNullable(current));
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final var response = serviceImplMock.submitAccountCreationRequest(accountRequestDTO);
        final var accountResponseDTO = AccountResponseDTO.builder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.COMPLETED)
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .build();

        // Then
        verify(customerAccountRepository).save(any(AccountRequest.class));
        assertThat(response).isEqualTo(accountResponseDTO);
    }

    @Test
    void shouldNotSaveAccountCreationIfStatusCompleted() {
        // Given
        accountRequestDTO = accountRequestDTO.toBuilder().withRequestId(REQUEST_ID).build();
        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.COMPLETED)
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .build();
        when(customerAccountRepository.findById(ID)).thenReturn(Optional.ofNullable(expected));
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> serviceImplMock.submitAccountCreationRequest(accountRequestDTO),
                "Expected submitAccountCreationRequest() to throw, but it didn't"
        );

        // Then
        assertEquals("Cannot update request. Account opening request is already completed for requestId "
                + REQUEST_ID, exception.getMessage());
    }

    @Test
    void shouldNotSaveAccountCreationIfIdOrRequestIdIsInvalid() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.COMPLETED)
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .build();
        when(customerAccountRepository.findById(ID)).thenReturn(Optional.ofNullable(expected));
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        final BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> serviceImplMock.submitAccountCreationRequest(accountRequestDTO),
                "Expected submitAccountCreationRequest() to throw, but it didn't"
        );

        // Then
        assertEquals("Invalid requestId null", exception.getMessage());
    }

    @Test
    void shouldGetAccountRequestById() {
        // Given
        final var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(Optional.ofNullable(expected));

        // When
        final var response = serviceImplMock.getAccountRequestById(REQUEST_ID);
        final var accountResponseDTO = AccountResponseDTO.builder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();

        // Then
        assertThat(response).isEqualTo(accountResponseDTO);
    }
}
