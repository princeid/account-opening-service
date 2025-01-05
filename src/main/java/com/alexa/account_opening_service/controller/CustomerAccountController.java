package com.alexa.account_opening_service.controller;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.exception.BadRequestException;
import com.alexa.account_opening_service.service.CustomerAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account-opening/request")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createRequest(@RequestBody @Validated AccountRequestDTO requestDto,
                                                            BindingResult bindingResult) {
        if (requestDto.getId() != null || requestDto.getRequestId() != null) {
            throw new BadRequestException("A new request cannot already have an id or requestId");
        }
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("One or more fields are Invalid");
        }
        AccountResponseDTO response = customerAccountService.beginAccountCreation(requestDto);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping
    public ResponseEntity<AccountResponseDTO> updateRequest(@RequestBody @Validated AccountRequestDTO requestDto,
                                                            BindingResult bindingResult) {
        if (requestDto.getId() == null) {
            throw new BadRequestException("Id is required");
        }
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("One or more fields are Invalid");
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
    public ResponseEntity<AccountResponseDTO> submitRequest(@RequestBody @Validated AccountRequestDTO requestDto,
                                                            BindingResult bindingResult) {
        if (requestDto.getRequestId() == null) {
            throw new BadRequestException("requestId is required");
        }
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("One or more fields are Invalid");
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
