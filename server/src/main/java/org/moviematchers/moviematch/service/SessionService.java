package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.SessionManager;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.repository.InvitationRepository;
import org.moviematchers.moviematch.repository.SessionRepository;
import org.moviematchers.moviematch.strategy.TheMovieDBFetchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final InvitationRepository invitationRepository;
    private final TheMovieDBFetchStrategy theMovieDB;
    @Autowired
    public SessionService(SessionRepository sessionRepository,
                          InvitationRepository invitationRepository,
                          TheMovieDBFetchStrategy theMovieDB) {
        this.sessionRepository = sessionRepository;
        this.invitationRepository = invitationRepository;
        this.theMovieDB = theMovieDB;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public void createSession(Session session) {
        sessionRepository.save(session);
    }

    public int joinSession(Long sessionID, Long userID) {
        Optional<Session> sessionByID = sessionRepository.findById(sessionID);
        if (sessionByID.isEmpty()) return -1;
        Session session = sessionByID.get();

        Optional<Invitation> invitationOfSession = invitationRepository.findById(session.getInvitationID());
        Invitation invitation = invitationOfSession.orElseGet(Invitation::new);
        SessionManager.sessionCurrentMovieFilter.put(sessionID, SessionManager.InvitationFiltersToConsumerMovieFilter(invitation));

        SessionManager.sessionMovies.put(sessionID, new ArrayList<>());
        SessionManager.sessionCurrentMovieIndex.put(sessionID, new Integer[2]);
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[0] = 0;
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[1] = 0;

        if(Objects.equals(session.getUser1ID(), userID)) return 0;
        else if(Objects.equals(session.getUser2ID(), userID)) return 1;
        else return -1;

    }

    public boolean addMovies(Long sessionID) {
        Optional<Session> sessionByID = sessionRepository.findById(sessionID);
        if (sessionByID.isEmpty()) return false;

        List<Movie> newMovies = theMovieDB.fetch(options -> {}, SessionManager.sessionCurrentMovieFilter.get(sessionID));
        SessionManager.sessionMovies.get(sessionID).addAll(newMovies);

        return true;
    }

    public Movie getCurrentMovie(Long sessionID, Integer userNumber) {
        return SessionManager.sessionMovies.get(sessionID).get(SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber]);
    }

    public void nextMovie(Long sessionID, int userNumber) {
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber]++;
    }

    public List<Movie> getCurrentListOfMovie(Long sessionID) {
        return SessionManager.sessionMovies.get(sessionID);
    }
}
