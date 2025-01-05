package com.alexa.account_opening_service.dto;

import com.alexa.account_opening_service.entity.AccountType;
import com.alexa.account_opening_service.entity.Address;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
public class AccountRequestDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Address address;
    @NotBlank(message = "Date of Birth cannot be blank")
    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$",
            message = "Date of Birth must be in the format DD-MM-YYYY"
    )
    private String dateOfBirth;
    private String idDocument;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Double startingBalance;
    private Double monthlySalary;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "Y|N")
    private String interestedInOtherProducts;
    private String requestId;
}
