package com.alexa.account_opening_service.repository;

import com.alexa.account_opening_service.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerAccountRepositoryTest {
    private static final Long ID = 1L;
    private static final String REQUEST_ID = "random-uuid-request-id-1";

    private final CustomerAccountRepository customerAccountRepository = mock(CustomerAccountRepository.class);
    private AccountRequest accountRequest;


    @BeforeEach
    public void setup() {
        // Setup mock data
        accountRequest = AccountRequest.builder()
                .withId(ID)
                .withName("Alexa")
                .withRequestId(REQUEST_ID)
                .withAddress(Address.builder()
                        .withHouseNumber("1")
                        .withStreet("Cornerstone Street")
                        .withCity("Amsterdam")
                        .withPostalCode("1234 AB")
                        .build())
                .withDateOfBirth("1990-10-05")
                .withIdDocument("http//example.com.doc-01.pdf")
                .withAccountType(AccountType.SAVINGS)
                .withState(State.STARTED)
                .withStatus(Status.PENDING)
                .withCreationDateTime(Instant.now())
                .withUpdateDateTime(Instant.now())
                .build();
    }

    @Test
    void shouldFindCustomerAccountByRequestId() {
        // Given
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(Optional.ofNullable(accountRequest));

        // When
        Optional<AccountRequest> actual = customerAccountRepository.findByRequestId(REQUEST_ID);

        // Then
        assertNotNull(actual);
        assertEquals(REQUEST_ID, actual.get().getRequestId());
        verify(customerAccountRepository, times(1)).findByRequestId(REQUEST_ID);
    }

    @Test
    void shouldNotFindCustomerAccountByRequestId() {
        // Given
        when(customerAccountRepository.findByRequestId(REQUEST_ID)).thenReturn(null);

        // When
        var actual = customerAccountRepository.findByRequestId(REQUEST_ID);

        // Then
        assertNull(actual);
        verify(customerAccountRepository, times(1)).findByRequestId(REQUEST_ID);
    }
}
