package com.alexa.account_opening_service.service;


import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.*;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.impl.CustomerAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerAccountServiceImplTest {
    private static final Long ID = 1L;
    private static final String REQUEST_ID = "random-uuid-request-id-1";

    private final CustomerAccountRepository customerAccountRepository = mock(CustomerAccountRepository.class);

    @InjectMocks
    private CustomerAccountServiceImpl customerAccountServiceImpl;

    private AccountRequestDTO accountRequestDTO;
    private AccountRequest accountRequest;

    @BeforeEach
    public void setup() {
        // Setup mock data
        accountRequestDTO = AccountRequestDTO.builder()
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

    @Test
    public void shouldBeginAccountCreation() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        AccountResponseDTO response = customerAccountServiceImpl.beginAccountCreation(accountRequestDTO);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
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
    public void shouldUpdateAccountCreation() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.UPDATED)
                .withStatus(Status.PENDING)
                .withMessage(State.UPDATED.getMessage())
                .build();
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        AccountResponseDTO response = customerAccountServiceImpl.updateAccountCreation(accountRequestDTO.toBuilder()
                .withEmail("example@test.com")
                .withStartingBalance(2000.05D)
                .build());
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
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
    public void shouldPauseAccountCreation() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.PAUSED)
                .withStatus(Status.PENDING)
                .withMessage(State.PAUSED.getMessage())
                .build();
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(expected);
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        AccountResponseDTO response = customerAccountServiceImpl.pauseAccountCreation(REQUEST_ID);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
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
    public void shouldCompleteAccountCreation() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.COMPLETED)
                .withStatus(Status.CONFIRMED)
                .withMessage(State.COMPLETED.getMessage())
                .build();
        when(customerAccountRepository.save(any(AccountRequest.class))).thenReturn(expected);

        // When
        AccountResponseDTO response = customerAccountServiceImpl.submitAccountCreationRequest(accountRequestDTO);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
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
    public void shouldGetAccountRequestById() {
        // Given
        var expected = accountRequest.toBuilder()
                .withId(ID)
                .withRequestId(REQUEST_ID)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withMessage(State.STARTED.getMessage())
                .build();
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(expected);

        // When
        AccountResponseDTO response = customerAccountServiceImpl.getAccountRequestById(REQUEST_ID);
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
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
