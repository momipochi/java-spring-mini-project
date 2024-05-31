package com.example.mp5spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mp5spring.DTO.PlaylistDTO;
import com.example.mp5spring.DTO.ProductDTO;
import com.example.mp5spring.model.Playlist;
import com.example.mp5spring.repository.PlaylistRepository;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    /*
     * returns all playlists
     */
    public List<PlaylistDTO> getAllPlaylists() {
        try {
            List<PlaylistDTO> playlists = new ArrayList<>();
            System.out.println(playlists.size());

            playlistRepository.findAll().forEach(
                    x -> playlists.add(new PlaylistDTO(x.getId(), x.getPlaylistName(), x.getSavedProducts().size())));

            return playlists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @Param listId
     * returns all products inside a playlist
     */
    public List<ProductDTO> getProductFromPlaylist(Long listId) {
        try {
            List<ProductDTO> products = new ArrayList<>();

            playlistRepository.findById(listId).get().getSavedProducts()
                    .forEach(x -> products.add(new ProductDTO(x.getId(), x.getProductName(),x.getDescription())));
            System.out.println(products.size());
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
     * @Param listId. videoIndex
     * remove video at index videoIndex from list identified by listId
     * returns Long as status
     */
    public Long removeVideoFromPlaylist(Long listId, int videoIndex){
        try {
            boolean existsPlaylist = playlistRepository.existsById(listId);
            if(!existsPlaylist){
                System.out.println("No playlist found");
                return 0L;
            }
            Playlist playlist = playlistRepository.findById(listId).get();
            if(playlist.getSavedProducts().size() < videoIndex){
                return 0L;
            }
            playlist.getSavedProducts().remove(videoIndex);
            playlistRepository.save(playlist);
            return 1L;
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            return -1L;
        }
    }
}
