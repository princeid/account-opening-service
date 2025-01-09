package com.alexa.account_opening_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "street cannot be blank")
    private String street;
    @NotBlank(message = "houseNumber cannot be blank")
    private String houseNumber;
    @NotBlank(message = "postalCode cannot be blank")
    @Pattern(
            regexp = "^[0-9]{4} [A-Z]{2}$",
            message = "postalCode must be in the format '1234 AB' (4 digits, space, 2 uppercase letters)"
    )
    private String postalCode;
    @NotBlank(message = "city cannot be blank")
    private String city;
}
