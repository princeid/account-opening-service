package com.alexa.account_opening_service.controller;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.service.CustomerAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account-opening/request")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createRequest(@Valid @RequestBody AccountRequestDTO requestDto) {
        AccountResponseDTO response = customerAccountService.beginAccountCreation(requestDto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<AccountResponseDTO> updateRequest(@PathVariable String requestId, @Valid @RequestBody AccountRequestDTO requestDto) {
        AccountResponseDTO response = customerAccountService.updateAccountCreation(requestId, requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/pause")
    public ResponseEntity<AccountResponseDTO> pauseRequest(@PathVariable String requestId) {
        AccountResponseDTO response = customerAccountService.pauseAccountCreation(requestId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/resume")
    public ResponseEntity<AccountResponseDTO> resumeRequest(@PathVariable String requestId) {
        AccountResponseDTO response = customerAccountService.resumeAccountCreation(requestId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/submit")
    public ResponseEntity<AccountResponseDTO> submitRequest(@PathVariable String requestId) {
        AccountResponseDTO response = customerAccountService.submitAccountCreationRequest(requestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<AccountRequestDTO> getAccountRequest(@PathVariable String requestId) {
        AccountRequestDTO accountRequest = customerAccountService.getAccountRequestById(requestId);
        return ResponseEntity.ok(accountRequest);
    }

    @GetMapping("testAPI")
    public String testMethod() {
        return "All good...";
    }
}
