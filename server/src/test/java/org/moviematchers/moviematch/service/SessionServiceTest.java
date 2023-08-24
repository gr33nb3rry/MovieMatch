package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.SessionManager;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieImpl;
import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.repository.InvitationRepository;
import org.moviematchers.moviematch.repository.SessionRepository;
import org.moviematchers.moviematch.strategy.TheMovieDBFetchStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private TheMovieDBFetchStrategy theMovieDB;
    private SessionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SessionService(sessionRepository, invitationRepository, theMovieDB);
    }
    @Test
    void canGetAllSessions() {
        // when
        underTest.getAllSessions();
        // then
        verify(sessionRepository).findAll();
    }

    @Test
    void canCreateSession() {
        // given
        Long user1Id = 1L;
        Long user2Id = 2L;
        Session session = new Session(1L, 1L, user1Id, user2Id);
        // when
        underTest.createSession(session);
        // then
        ArgumentCaptor<Session> argumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepository).save(argumentCaptor.capture());

        Session captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(session);
    }

    @Test
    void canJoinSession() {
        // given
        Long userId = 1L;
        Long user1Id = 1L;
        Long user2Id = 2L;
        Session session = new Session(1L, 1L, user1Id, user2Id);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        // when
        int result = underTest.joinSession(1L, userId);
        // then
        verify(sessionRepository).findById(anyLong());

        assertThat(result).isEqualTo(0);
    }

    @Test
    void canAddMovies() {
        // too hard, I'm stuck
        // given
        Long sessionID = 2L;
        // when
        boolean result = underTest.addMovies(sessionID);
        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void getCurrentMovie() {
        // given
        Long sessionId = 1L;
        int userNumber = 0;
        List<Movie> fetchedMovies = new ArrayList<>();
        fetchedMovies.add(new MovieImpl("Movie 1", "Description 1", LocalDate.now(), 7.5, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 2", "Description 2", LocalDate.now(), 8.0, null, false, null));
        SessionManager.sessionMovies.put(sessionId, fetchedMovies);
        SessionManager.sessionCurrentMovieIndex.put(sessionId, new Integer[2]);
        SessionManager.sessionCurrentMovieIndex.get(sessionId)[0] = 0;
        SessionManager.sessionCurrentMovieIndex.get(sessionId)[1] = 0;
        // when
        Movie result = underTest.getCurrentMovie(sessionId, userNumber);
        // then
        assertThat(result.getTitle()).isEqualTo("Movie 1");
    }

    @Test
    void increaseCurrentMovieIndex() {
        // given
        Long sessionId = 1L;
        int userNumber = 0;
        List<Movie> fetchedMovies = new ArrayList<>();
        fetchedMovies.add(new MovieImpl("Movie 1", "Description 1", LocalDate.now(), 7.5, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 2", "Description 2", LocalDate.now(), 8.0, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 3", "Description 3", LocalDate.now(), 7.5, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 4", "Description 4", LocalDate.now(), 8.0, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 5", "Description 5", LocalDate.now(), 7.5, null, false, null));
        fetchedMovies.add(new MovieImpl("Movie 6", "Description 6", LocalDate.now(), 8.0, null, false, null));
        SessionManager.sessionMovies.put(sessionId, fetchedMovies);
        SessionManager.sessionCurrentMovieIndex.put(sessionId, new Integer[2]);
        SessionManager.sessionCurrentMovieIndex.get(sessionId)[0] = 0;
        SessionManager.sessionCurrentMovieIndex.get(sessionId)[1] = 0;
        // when
        underTest.increaseCurrentMovieIndex(sessionId, userNumber);
        // then
        assertThat(SessionManager.sessionCurrentMovieIndex.get(sessionId)[userNumber]).isEqualTo(1);
    }

    @Test
    @Disabled
    void getCurrentListOfMovie() {
    }

    @Test
    @Disabled
    void skipMovie() {
    }

    @Test
    @Disabled
    void likeMovie() {
    }

    @Test
    @Disabled
    void returnLastMovie() {
    }

    @Test
    @Disabled
    void getLikedMovies() {
    }

    @Test
    @Disabled
    void getMatches() {
    }

    @Test
    @Disabled
    void getMatchCount() {
    }

    @Test
    @Disabled
    void checkForNewMatch() {
    }

    @Test
    @Disabled
    void getLastMatch() {
    }
}