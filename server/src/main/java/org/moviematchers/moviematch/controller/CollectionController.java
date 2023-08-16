package org.moviematchers.moviematch.controller;

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
    @PostMapping
    public void addCollection(@RequestBody UserMovieCollection userMovieCollection) {
        collectionService.addCollection(userMovieCollection);
    }
}