package com.example.mp5spring.model;

import java.time.LocalDateTime;
import java.time.Period;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name should not be blank")
    @Size(min = 2, max = 255)
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    @Size(min = 2, max = 255)
    private String lastName;

    @Pattern(regexp = "\\d{11}", message = "Pesel does not match pattern")
    @NotBlank(message = "Pesel should not be blank")
    private String pesel;

    @NotNull(message = "Date of birth needs to be specified")
    private LocalDateTime dateOfBirth;

    @Pattern(regexp = "^(?i)F|M$", message = "Not a gender")
    @NotBlank(message = "Gender should not be blank")
    private String sex;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonBackReference
    @NotNull(message = "Address should not be null")
    private Address livesAt;

    public Person() {
    }

    public Person(Person person) {
        defaultSetter(person.getFirstName(), person.getLastName(), person.getDateOfBirth(), person.getPesel(),
                person.getSex());
    }

    public Person(String firstName, String lastName, String pesel, LocalDateTime dateOfBirth, String sex) {
        defaultSetter(firstName, lastName, dateOfBirth, sex, pesel);
    }

    private void defaultSetter(String firstName, String lastName, LocalDateTime dateOfBirth, String sex, String pesel) {
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setSex(sex);
        setPesel(pesel);
    }

    // @Transient
    public int getAge() {
        if (this.dateOfBirth != null) {
            return Period.between(this.dateOfBirth.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
        }
        return 0;
    }

}
