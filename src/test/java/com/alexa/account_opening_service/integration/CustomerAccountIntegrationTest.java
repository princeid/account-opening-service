package com.alexa.account_opening_service.integration;

import com.alexa.account_opening_service.controller.CustomerAccountController;
import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.entity.Address;
import com.alexa.account_opening_service.entity.State;
import com.alexa.account_opening_service.entity.Status;
import com.alexa.account_opening_service.repository.CustomerAccountRepository;
import com.alexa.account_opening_service.service.CustomerAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CustomerAccountIntegrationTest {
    private static final Random RANDOM = new Random();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final CustomerAccountService customerAccountService;
    private final CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountIntegrationTest(final CustomerAccountService customerAccountService, final CustomerAccountRepository customerAccountRepository) {
        this.customerAccountService = customerAccountService;
        this.customerAccountRepository = customerAccountRepository;
    }

    private AccountRequestDTO accountRequestDTO;

    @BeforeEach
    void setUp() {
        // Mock the controller with mock customer account service
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerAccountController(customerAccountService))
                .build();
    }

    @Test
    void shouldReturn200WhenBeginRequestIsValid() throws Exception {
        accountRequestDTO = AccountRequestDTO.builder()
                .withName("Test Name")
                .withEmail("test" + RANDOM.nextLong() + "@test.com")
                .withDateOfBirth("01-01-1990")
                .withAddress(
                        Address.builder()
                                .withHouseNumber("1")
                                .withStreet("Street 1")
                                .withPostalCode("1033 AB")
                                .withCity("City 1")
                                .build()
                )
                .build();
        mockMvc.perform(post("/api/v1/account-opening/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.requestId").isNotEmpty())
                .andExpect(jsonPath("$.status").value(Status.PENDING.name()))
                .andExpect(jsonPath("$.state").value(State.STARTED.name()))
                .andDo(print());
        // Then
        assertNotNull(customerAccountRepository.findByRequestId("12345"));
    }

    @Test
    void shouldReturn400ForUpdateWhenIdIsNull() throws Exception {
        mockMvc.perform(put("/api/v1/account-opening/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldReturn400WhenPauseRequestIdIsNull() throws Exception {
        mockMvc.perform(post("/api/v1/account-opening/request/{requestId}/pause", ""))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturn404WhenRequestIdIsEmpty() throws Exception {
        mockMvc.perform(get("/api/v1/account-opening/request/{requestId}", ""))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
