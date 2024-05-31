package com.example.mp5spring.model;

import com.example.interfaces.IStream;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArchivedStream extends Video implements IStream {
    @Builder.Default
    private boolean allowLiveCommentReplay = false;

    @Override
    public boolean haveLiveCommentReplay() {
        // TODO Auto-generated method stub
        if (!super.isLiveComment()) {
            return false;
        }
        if (allowLiveCommentReplay) {
            return true;
        }
        return false;
    }
}
