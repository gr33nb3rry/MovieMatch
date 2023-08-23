package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("byInvite")
    public ResponseEntity<Long> getSessionIDByInvite(@RequestParam Long invitationID) {
        Long result =  sessionService.getSessionIDByInvite(invitationID);
        if (result != -1L) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("create") //returns session_id
    public ResponseEntity<Long> createSession(@RequestBody Session session) {
        boolean result = sessionService.createSession(session);
        if (result) {
            return new ResponseEntity<>(session.getSessionID(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("join") // for frontend: if response is 0 or 1 than open session.html, -1 is failure
    public ResponseEntity<Integer> joinSession(@RequestParam Long sessionID, @RequestParam Long userID) {
        int result = sessionService.joinSession(sessionID, userID);
        if (result == 0 || result == 1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("addMovies")
    public ResponseEntity<String> addMovies(@RequestParam Long sessionID) {
        boolean result = sessionService.addMovies(sessionID);
        if (result) {
            return new ResponseEntity<>("Movies successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add movies", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getCurrent")
    public Movie getCurrentMovie(@RequestParam Long sessionID, @RequestParam Integer userNumber) {
        return sessionService.getCurrentMovie(sessionID, userNumber);
    }
    @GetMapping("getCurrentList")
    public List<Movie> getCurrentListOfMovie(@RequestParam Long sessionID) {
        return sessionService.getCurrentListOfMovie(sessionID);
    }
    @PostMapping("skip")
    public Movie skipMovie(@RequestParam Long sessionID, @RequestParam int userNumber) {
        return sessionService.skipMovie(sessionID, userNumber);
    }
    @PostMapping("like")
    public Movie likeMovie(@RequestParam Long sessionID, @RequestParam int userNumber) {
        return sessionService.likeMovie(sessionID, userNumber);
    }
    @PostMapping("return")
    public Movie returnLastMovie(@RequestParam Long sessionID, @RequestParam int userNumber) {
        return sessionService.returnLastMovie(sessionID, userNumber);
    }
    @GetMapping("likedMovies")
    public List<Movie> getLikedMovies(@RequestParam Long sessionID, @RequestParam int userNumber) {
        return sessionService.getLikedMovies(sessionID, userNumber);
    }
    @GetMapping("allMatches")
    public List<Movie> getMatches(@RequestParam Long sessionID) {
        return sessionService.getMatches(sessionID);
    }
    @GetMapping("matchCount")
    public int getMatchCount(@RequestParam Long sessionID) {
        return sessionService.getMatchCount(sessionID);
    }
    @GetMapping("match")
    public boolean checkForNewMatch(@RequestParam Long sessionID) {
        return sessionService.checkForNewMatch(sessionID);
    }
    @GetMapping("lastMatch")
    public Movie getLastMatch(@RequestParam Long sessionID) {
        return sessionService.getLastMatch(sessionID);
    }
}
