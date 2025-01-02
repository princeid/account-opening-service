package com.alexa.account_opening_service.entity;

import com.alexa.account_opening_service.model.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "ADDRESS")
public class Address extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Street name cannot be blank")
    private String street;
    @NotBlank(message = "House number cannot be blank")
    private String houseNumber;
    @NotBlank(message = "Postcode cannot be blank")
    @Pattern(
            regexp = "^[0-9]{4} [A-Z]{2}$",
            message = "Postcode must be in the format '1234 AB' (4 digits, space, 2 uppercase letters)"
    )
    private String postalCode;
    @NotBlank(message = "City cannot be blank")
    private String city;
}
