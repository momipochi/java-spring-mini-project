package com.example.mp5spring.model;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Video extends Product {
    @Builder.Default
    private boolean liveComment = false;
    @Min(1000)
    @NotNull
    private double durationInMs;

    public static double incomePerView = 0.5;

    public static double sharePercentage = 0.1;

    public static double monetizationThreshold = 10000000;

    @Override
    public double calculateRevenue() {
        // TODO Auto-generated method stub
        if (views * incomePerView * sharePercentage > monetizationThreshold) {
            return views * incomePerView * (sharePercentage + 0.1);
        }
        return views * incomePerView * sharePercentage;
    }

}
