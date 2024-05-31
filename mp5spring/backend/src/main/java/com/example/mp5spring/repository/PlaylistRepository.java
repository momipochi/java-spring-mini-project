package com.example.mp5spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mp5spring.model.Playlist;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Query("from Playlist p inner join p.savedBy sb where sb.id = :channelId")
    public List<Playlist> findAllPlaylistSavedByChannel(@Param ("channelId") Long channelId);
}
