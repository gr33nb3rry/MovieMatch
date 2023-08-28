package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieImpl;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.repository.CollectionRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;
    @Mock
    private MovieService movieService;
    private CollectionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CollectionService(collectionRepository, movieService);
    }

    @Test
    void canAddMovieToCollection() {
        // given
        UserEntity user = new UserEntity(1L, "name");
        UserMovieCollection collection = new UserMovieCollection(user, "Movie title", 10);
        // when
        underTest.addCollection(collection);
        // then
        ArgumentCaptor<UserMovieCollection> argumentCaptor = ArgumentCaptor.forClass(UserMovieCollection.class);
        verify(collectionRepository).save(argumentCaptor.capture());

        UserMovieCollection captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(collection);
    }
    @Test
    void canGetAllCollectionsOfUser() {
        // when
        underTest.getAllCollectionsOfUser(1L);
        // then
        verify(collectionRepository).findByUserEntityId(1L);
    }

    @Test
    void canGetMovieFromAPIByName() {
        // given
        String test = "Batman";
        List<Movie> mockedMovies = new ArrayList<>();
        Movie mockMovie = new MovieImpl(test, "Desc", LocalDate.now(), 5.5, null, false, null);
        mockedMovies.add(mockMovie);
        when(movieService.fetch(any(), eq(test))).thenReturn(mockedMovies);
        // when
        underTest.getMovieFromAPIByName(test);
        //then
        verify(movieService).fetch(any(), eq(test));
    }
}