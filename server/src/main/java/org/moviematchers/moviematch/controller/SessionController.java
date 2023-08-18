package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "session")
public class SessionController {
    private final SessionService sessionService;
    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("all")
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }
    @PostMapping //returns session_id
    public Long createSession(@RequestBody Session session) {
        sessionService.createSession(session);
        return session.getSessionID();
    }
    @GetMapping("join") // for frontend: if response is "Joined" open session.html
    public String joinSession(@RequestParam Long sessionID, @RequestParam Long userID) {
        boolean response = sessionService.joinSession(sessionID, userID);
        if (response) return "Joined";
        else return "Failed";
    }
}
