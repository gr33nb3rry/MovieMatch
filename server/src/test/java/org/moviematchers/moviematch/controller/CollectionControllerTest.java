package org.moviematchers.moviematch.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.service.CollectionService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CollectionControllerTest {
    @Mock
    private CollectionService service;
    private CollectionController underTest;

    @BeforeEach
    void setUp() {
        underTest = new CollectionController(service);
    }

    @Test
    void getAllCollectionsOfUser() {
        // given
        Long userId = 1L;
        // when
        underTest.getAllCollectionsOfUser(userId);
        // then
        verify(service).getAllCollectionsOfUser(userId);
    }

    @Test
    void getMovieFromAPIByName() {
        // given
        String movieTitle = "Batman";
        // when
        underTest.getMovieFromAPIByName(movieTitle);
        // then
        verify(service).getMovieFromAPIByName(movieTitle);
    }

    @Test
    void addCollection() {
        // given
        UserEntity user = new UserEntity(1L, "name");
        UserMovieCollection collection = new UserMovieCollection(user, "Movie title", 10);
        // when
        underTest.addCollection(collection);
        // then
        verify(service).addCollection(collection);
    }
}