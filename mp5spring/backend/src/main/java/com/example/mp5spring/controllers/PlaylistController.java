package com.example.mp5spring.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mp5spring.DTO.PlaylistDTO;
import com.example.mp5spring.DTO.ProductDTO;
import com.example.mp5spring.services.PlaylistService;

@CrossOrigin(origins = Constants.baseUrl)
@RestController
@RequestMapping(Constants.baseMappingPlaylist)
public class PlaylistController {

    @PersistenceContext
    private EntityManager entityManager;

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /*
     * returns all playlists
     */
    @GetMapping(Constants.GET_ALL_PLAYLISTS)
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        System.out.println("get all playlists called");
        List<PlaylistDTO> playlists = playlistService.getAllPlaylists();
        if (playlists == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (playlists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playlists, HttpStatus.OK);

    }

    /*
     * @Param listId
     * returns all products inside a playlist
     */
    @SuppressWarnings("null")
    @GetMapping("/getProductsFromPlaylist")
    public ResponseEntity<List<ProductDTO>> getProductFromPlaylist(@RequestParam("listId") Long listId) {
        System.out.println("Im here getting product from playlist");
        if (listId < 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ProductDTO> products = playlistService.getProductFromPlaylist(listId);
        if (products == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
     * @Param listId. videoIndex
     * remove video at index videoIndex from list identified by listId
     * returns differnt HttpStatus based on number returned from service
     */
    @DeleteMapping("/removeVideoFromPlaylist")
    public ResponseEntity<Long> removeVideoFromPlaylist(@RequestParam("listId") Long listId,
            @RequestParam("videoIndex") int videoIndex) {
        System.out.println("List id: " + listId + " Index: " + videoIndex);
        if (videoIndex < 0 || listId < 0) {
            System.out.println("videoIndex or listId smaller than 0");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long actionResult = playlistService.removeVideoFromPlaylist(listId, videoIndex);
        if (actionResult == -1) {
            System.out.println("internal error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (actionResult == 0) {
            System.out.println("no content");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
