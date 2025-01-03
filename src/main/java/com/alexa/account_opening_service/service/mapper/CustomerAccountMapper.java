package com.alexa.account_opening_service.service.mapper;

import com.alexa.account_opening_service.dto.AccountRequestDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import jakarta.validation.Valid;

public final class CustomerAccountMapper {

    private CustomerAccountMapper() {
        // static access only
    }

    public static AccountRequest mapToEntity(@Valid final AccountRequestDTO accountRequestDTO) {
        return AccountRequest.builder()
                .withName(accountRequestDTO.getName() != null ? accountRequestDTO.getName() : "")
                .withAddress(accountRequestDTO.getAddress() != null ? accountRequestDTO.getAddress() : null)
                .withDateOfBirth(accountRequestDTO.getDateOfBirth() != null ? accountRequestDTO.getDateOfBirth() : null)
                .withAccountType(accountRequestDTO.getAccountType() != null ? accountRequestDTO.getAccountType() : null)
                .withEmail(accountRequestDTO.getEmail() != null ? accountRequestDTO.getEmail() : "")
                .withInterestedInOtherProducts(accountRequestDTO.getInterestedInOtherProducts() != null ?
                        accountRequestDTO.getInterestedInOtherProducts() : null)
                .withIdDocument(accountRequestDTO.getIdDocument() != null ? accountRequestDTO.getIdDocument() : null)
                .withMonthlySalary(accountRequestDTO.getMonthlySalary() != null ? accountRequestDTO.getMonthlySalary() : null)
                .withStartingBalance(accountRequestDTO.getStartingBalance() != null ? accountRequestDTO.getStartingBalance() : null)
                .build();
    }
}
