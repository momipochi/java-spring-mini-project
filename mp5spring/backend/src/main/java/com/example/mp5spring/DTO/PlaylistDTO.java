package com.example.mp5spring.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PlaylistDTO {
    private Long id;
    private String playlistName;
    private int size;

    public PlaylistDTO(Long id, String playlistName, int size) {
        this.id = id;
        this.playlistName = playlistName;
        this.size = size;
    }
}
