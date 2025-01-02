package com.alexa.account_opening_service.entity;

import com.alexa.account_opening_service.model.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERACCOUNT")
public class AccountRequest extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @OneToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;
    @NotBlank(message = "Date of Birth cannot be blank")
    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$",
            message = "Date of Birth must be in the format DD-MM-YYYY"
    )
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
    private String requestId;
    private String status;
    private String message;
}
