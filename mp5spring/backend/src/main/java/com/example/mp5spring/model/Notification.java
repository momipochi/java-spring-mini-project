package com.example.mp5spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.example.mp5spring.validation.NotificationOrConstraint;

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
@NotificationOrConstraint
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userAccount_id", updatable = false, nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Receiver cannot be null")
    private UserAccount notificationReceiver;

    @OneToOne(mappedBy = "notificationEmail")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmailNotification emailNotification;

    @OneToOne(mappedBy = "notificationSms")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SmsNotification smsNotification;
}
