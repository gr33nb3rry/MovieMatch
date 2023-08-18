package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.dto.Movie;
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
    @PostMapping("join") // for frontend: if response is 0 or 1 than open session.html, -1 is failure
    public int joinSession(@RequestParam Long sessionID, @RequestParam Long userID) {
        return sessionService.joinSession(sessionID, userID);
    }
    @PostMapping("addMovies") // for frontend: if response is "Joined" open session.html
    public String addMovies(@RequestParam Long sessionID) {
        boolean response = sessionService.addMovies(sessionID);

        if (response) return "Added";
        else return "Failed";
    }
    @GetMapping("getCurrent")
    public Movie getCurrentMovie(@RequestParam Long sessionID, @RequestParam Integer userNumber) {
        return sessionService.getCurrentMovie(sessionID, userNumber);
    }
    @GetMapping("getCurrentList")
    public List<Movie> getCurrentListOfMovie(@RequestParam Long sessionID) {
        return sessionService.getCurrentListOfMovie(sessionID);
    }
    @PostMapping("next")
    public void nextMovie(@RequestParam Long sessionID, @RequestParam int userNumber){
        sessionService.nextMovie(sessionID, userNumber);
    }
}
