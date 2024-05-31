package com.example.mp5spring.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Product needs a name")
    @NotNull(message = "Product name should not be null")
    private String productName;

    private String description;

    @Builder.Default
    @Min(0)
    public double views = 0;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Channel ownedByChannel;

    @ManyToOne
    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Channel ownedByChannelPrivate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_of_watchQueue", joinColumns = @JoinColumn(name = "watchQueue_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @Builder.Default
    private List<WatchQueue> savedInQueues = new ArrayList<>();

    @ManyToMany(mappedBy = "savedProducts",fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @Builder.Default
    private List<Playlist> savedInPlaylists = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "moderator_of_product", joinColumns = @JoinColumn(name = "userAccount_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<UserAccount> moderators = new HashSet<>();

    @OneToMany(mappedBy = "productCommentedOn", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "id"))
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "id"))
    private List<String> tags;

    public abstract double calculateRevenue();
}
