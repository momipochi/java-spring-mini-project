package com.example.mp5spring.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class EmailNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "No source email address given")
    @Pattern(regexp = "^(.+)@(\\S+)$",message = "Not an email")
    private String fromEmail;

    @NotNull(message = "No destination email address given")
    @Pattern(regexp = "^(.+)@(\\S+)$",message = "Not an email")
    private String toEmail;

    @Size(max = 255, message = "Subject of email is too long")
    private String subject;

    private String body;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(optional = true, cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "notification_id",updatable = false, referencedColumnName = "id")
    private Notification notificationEmail;
}
