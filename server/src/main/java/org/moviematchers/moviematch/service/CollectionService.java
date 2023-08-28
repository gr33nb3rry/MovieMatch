package org.moviematchers.moviematch.service;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.repository.CollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectionService {
    private final Logger logger = LoggerFactory.getLogger(CollectionService.class);
    private final CollectionRepository collectionRepository;
    private final MovieService movieService;
    @Autowired
    public CollectionService(CollectionRepository collectionRepository, MovieService movieService) {
        this.collectionRepository = collectionRepository;
        this.movieService = movieService;
    }

    public boolean addCollection(UserMovieCollection userMovieCollection) {
        try {
            logger.info("Add collection movie for user_id: {}", userMovieCollection.getUserEntity());
            logger.info("Add collection movie with movie_title: {}", userMovieCollection.getMovieTitle());

            collectionRepository.save(userMovieCollection);
            return true;
        }
        catch(Exception e) {
            return false;
        }

    }


    public List<UserMovieCollection> getAllCollectionsOfUser(Long id) {
        return collectionRepository.findByUserEntityId(id);
    }
    public Movie getMovieFromAPIByName(String movieTitle) {
        List<Movie> movies = this.movieService.fetch(options -> {}, movieTitle);
        logger.info("Fetched movie title: {}", movies.get(0).getTitle());
        logger.info("Fetched movie description: {}", movies.get(0).getDescription());
        logger.info("Fetched movie IMDB rating: {}", movies.get(0).getRating());
        logger.info("Fetched movie release date: {}", movies.get(0).getReleaseDate());
        return movies.get(0);
    }
}
