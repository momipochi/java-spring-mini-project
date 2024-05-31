package com.example.mp5spring.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.mp5spring.validation.ChannelProductSubsetConstraint;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ChannelProductSubsetConstraint
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Channel name should not be null")
    @Size(min = 5, max = 255, message = "Channel name too short or too long")
    private String channelName;

    @OneToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "userAccount_id", nullable = false, updatable = false)
    @JsonBackReference
    private UserAccount ownerOfChannel;

    @OneToMany(mappedBy = "ownedByChannel", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @Builder.Default
    @JsonBackReference
    private Set<Product> ownedProducts = new HashSet<>();

    @OneToMany(mappedBy = "ownedByChannelPrivate", fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @Builder.Default
    @JsonBackReference
    private Set<Product> ownedProductsPrivate = new HashSet<>();
    
    @ManyToMany(mappedBy = "savedBy",cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<Playlist> savedPlaylists = new ArrayList<>();
}
