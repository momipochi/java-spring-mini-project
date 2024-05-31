package com.example.mp5spring.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Street name should not be blank")
    @Size(min = 5, max = 255, message = "Street name too short or too long")
    private String streetName;

    @NotBlank(message = "Zip code should not be blank")
    @Size(min = 5, max = 10, message = "Zip code too short or too long")
    private String zipCode;

    @OneToOne(mappedBy = "livesAt")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Person person;

    // public Address() {
    // }

    // public Address(String streetName, String zipCode) {
    //     setStreetName(streetName);
    //     setZipCode(zipCode);
    // }
}
