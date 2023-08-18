package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public void createSession(Session session) {
        sessionRepository.save(session);
    }

    public boolean joinSession(Long sessionID, Long userID) {
        Optional<Session> sessionByID = sessionRepository.findById(sessionID);
        if (sessionByID.isEmpty()) return false;

        Session session = sessionByID.get();
        return Objects.equals(session.getUser1ID(), userID) ||
                Objects.equals(session.getUser2ID(), userID);
    }
}
