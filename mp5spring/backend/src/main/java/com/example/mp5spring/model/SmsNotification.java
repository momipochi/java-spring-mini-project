package com.example.mp5spring.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "No source phone number given")
    @Pattern(regexp = "\\d{9}",message = "Not a phone number")
    private String fromPhoneNumber;

    @NotBlank(message = "No destination phone number given")
    @Pattern(regexp = "\\d{9}",message = "Not a phone number")
    private String toPhoneNumber;

    private String body;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(optional = true, cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "notification_id", nullable = true,updatable = false, referencedColumnName = "id")
    private Notification notificationSms;
}
