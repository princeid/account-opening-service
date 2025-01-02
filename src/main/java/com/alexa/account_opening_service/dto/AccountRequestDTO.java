package com.alexa.account_opening_service.dto;

import com.alexa.account_opening_service.entity.Address;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotBlank
    private String name;
    @NotNull
    private Address address;
    @NotNull
    @Past
    private String dateOfBirth;
    private String idDocument;
    @Pattern(regexp = "SAVINGS|CURRENT|INVESTMENT")
    private String accountType;
    private Double startingBalance;
    private Double monthlySalary;
    @Email
    private String email;
    @Pattern(regexp = "Y|N")
    private String interestedInOtherProducts;
}
