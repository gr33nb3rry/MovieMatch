package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "collection")
public class CollectionController {
    private final CollectionService collectionService;
    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
    @GetMapping("byID")
    public List<UserMovieCollection> getAllCollectionsOfUser(@RequestParam Long id) {
        return collectionService.getAllCollectionsOfUser(id);
    }
    @GetMapping("fromDBbyName")
    public Movie getMovieFromAPIByName(@RequestParam String movieTitle) {
        return collectionService.getMovieFromAPIByName(movieTitle);
    }
    @PostMapping
    public ResponseEntity<String> addCollection(@RequestBody UserMovieCollection userMovieCollection) {
        boolean result = collectionService.addCollection(userMovieCollection);
        if (result) {
            return new ResponseEntity<>("Movie added to collection", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add movie to collection", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
