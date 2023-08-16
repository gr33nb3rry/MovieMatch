package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    // Use this movie_title in getMovieFromTheMovieDBByName() to get movie info
    @GetMapping("byID")
    public List<UserMovieCollection> getAllCollectionsOfUser(@RequestParam Long id) {
        return collectionService.getAllCollectionsOfUser(id);
    }
    // Get movie data as title, poster path, release date etc by movie title
    // ex. http://localhost:8080/collection/fromDBbyName?movieTitle=Spider-man 2002
    // avoid spaces, use encodeURIComponent in JS
    @GetMapping("fromDBbyName")
    public Movie getMovieFromTheMovieDBByName(@RequestParam String movieTitle) {
        return collectionService.getMovieFromTheMovieDBByName(movieTitle);
    }
    // Get list of rows of user_movie_collection table that user_id is {id} from param
    // ex. http://localhost:8080/collection/fromDBbyUserID?id=4
    // It returns Movie, that stores title, poster path, release date etc
    // avoid spaces, use encodeURIComponent in JS
    @GetMapping("fromDBbyUserID")
    public List<Movie> getAllCollectionsOfUserFromMovieDB(@RequestParam Long id) {
        return collectionService.getAllCollectionsOfUserFromMovieDB(id);
    }
    @PostMapping
    public void addCollection(@RequestBody UserMovieCollection userMovieCollection) {
        collectionService.addCollection(userMovieCollection);
    }
}
