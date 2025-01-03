package com.alexa.account_opening_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "CUSTOMERACCOUNT")
public class AccountRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
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
    private AccountType accountType;
    private Double startingBalance;
    private Double monthlySalary;
    @Email
    private String email;
    @Pattern(regexp = "Y|N")
    private String interestedInOtherProducts;
    @Column(unique = true)
    private String requestId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private State state;
    private String message;
    @CreatedDate
    private Instant creationDateTime;
    @LastModifiedDate
    private Instant updateDateTime;
}
