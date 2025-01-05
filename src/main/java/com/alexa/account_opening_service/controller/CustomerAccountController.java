package com.alexa.account_opening_service.controller;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.exception.BadRequestException;
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
        if (requestDto.getId() != null || requestDto.getRequestId() != null) {
            throw new BadRequestException("A new request cannot already have an Id");
        }
        AccountResponseDTO response = customerAccountService.beginAccountCreation(requestDto);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping
    public ResponseEntity<AccountResponseDTO> updateRequest(@Valid @RequestBody AccountRequestDTO requestDto) {
        if (requestDto.getId() == null) {
            throw new BadRequestException("Id is required");
        }
        AccountResponseDTO response = customerAccountService.updateAccountCreation(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/pause")
    public ResponseEntity<AccountResponseDTO> pauseRequest(@PathVariable String requestId) {
        if (requestId == null) {
            throw new BadRequestException("requestId is required");
        }
        AccountResponseDTO response = customerAccountService.pauseAccountCreation(requestId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<AccountResponseDTO> submitRequest(@Valid @RequestBody AccountRequestDTO requestDto) {
        if (requestDto.getRequestId() == null) {
            throw new BadRequestException("requestId is required");
        }
        AccountResponseDTO response = customerAccountService.submitAccountCreationRequest(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<AccountResponseDTO> getAccountRequest(@PathVariable String requestId) {
        if (requestId == null) {
            throw new BadRequestException("requestId is required");
        }
        AccountResponseDTO response = customerAccountService.getAccountRequestById(requestId);
        return ResponseEntity.ok(response);
    }
}
