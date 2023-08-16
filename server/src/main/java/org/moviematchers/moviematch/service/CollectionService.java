package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.repository.CollectionRepository;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addCollection(UserMovieCollection userMovieCollection) {
        System.out.println(userMovieCollection.getUserID());
        System.out.println(userMovieCollection.getMovieTitle());

        collectionRepository.save(userMovieCollection);
    }

    public List<UserMovieCollection> getAllCollections() {
        return collectionRepository.findAll();
    }
}
