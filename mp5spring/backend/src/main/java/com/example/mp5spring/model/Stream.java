package com.example.mp5spring.model;

import javax.persistence.Entity;

import com.example.interfaces.IStream;

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
public class Stream extends Product implements IStream {
    @Builder.Default
    private boolean liveComment = false;

    @Builder.Default
    private double earningFromSuperChat = 0;

    @Builder.Default
    private double realTimeDelayInMs = 15000;

    public static double incomePerView = 0.5;

    public static double sharePercentage = 0.2;

    public static double monetizationThreshold = 5000000;

    @Override
    public boolean haveLiveCommentReplay() {
        // TODO Auto-generated method stub
        return liveComment;
    }

    @Override
    public double calculateRevenue() {
        // TODO Auto-generated method stub
        if (views * incomePerView * sharePercentage > monetizationThreshold) {
            return views * incomePerView * (sharePercentage + 0.1) + earningFromSuperChat;
        }
        return views * incomePerView * sharePercentage + earningFromSuperChat;
    }
}
