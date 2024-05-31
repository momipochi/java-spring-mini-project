package com.example.mp5spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@SuperBuilder
@ToString
public class UserAccount extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username should not be blank")
    @Size(min = 3, max = 50,message = "Username too short or too long")
    private String username;

    @OneToOne(mappedBy = "ownerOfChannel", cascade = { CascadeType.REMOVE })
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Channel channel;

    @OneToMany(mappedBy = "notificationReceiver",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    private Set<Notification> notifications = new HashSet<>();

    @OneToOne(mappedBy = "ownerOfWatchQueue", cascade = { CascadeType.REMOVE })
    private WatchQueue watchQueue;

    @ManyToMany(mappedBy = "moderators")
    @Builder.Default
    private Set<Product> moderatorOf = new HashSet<>();

    @OneToMany(mappedBy = "commenter", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();

    @Builder.Default
    private boolean banned = false;

    public UserAccount() {
    }

    public UserAccount(Employee emp, String username) {
        super(emp);
        this.username = username;
    }
}
