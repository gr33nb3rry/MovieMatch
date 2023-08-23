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
    @GetMapping
    public List<UserMovieCollection> getAllCollections() {
        return collectionService.getAllCollections();
    }
    // Get list of rows of user_movie_collection table that user_id is {id} from param
    // It returns UserMovieCollection, that stores movie_title and user_rating
    // Use this movie_title in getMovieFromAPIByName() to get movie info
    @GetMapping("byID")
    public List<UserMovieCollection> getAllCollectionsOfUser(@RequestParam Long id) {
        return collectionService.getAllCollectionsOfUser(id);
    }
    // Get movie data as title, poster path, release date etc by movie title
    // ex. http://localhost:8080/collection/fromDBbyName?movieTitle=Spider-man 2002
    // avoid spaces, use encodeURIComponent in JS
    @GetMapping("fromDBbyName")
    public Movie getMovieFromAPIByName(@RequestParam String movieTitle) {
        return collectionService.getMovieFromAPIByName(movieTitle);
    }
    // Get list of rows of user_movie_collection table that user_id is {id} from param
    // ex. http://localhost:8080/collection/fromDBbyUserID?id=4
    // It returns Movie, that stores title, poster path, release date etc
    // avoid spaces, use encodeURIComponent in JS
    @GetMapping("fromDBbyUserID")
    public List<Movie> getAllCollectionsOfUserAPI(@RequestParam Long id) {
        return collectionService.getAllCollectionsOfUserFromAPI(id);
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
