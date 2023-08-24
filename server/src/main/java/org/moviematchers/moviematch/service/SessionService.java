package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.SessionManager;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.entity.Session;
import org.moviematchers.moviematch.repository.InvitationRepository;
import org.moviematchers.moviematch.repository.SessionRepository;
import org.moviematchers.moviematch.strategy.TheMovieDBFetchStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final Logger logger = LoggerFactory.getLogger(SessionService.class);
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

    public boolean createSession(Session session) {
        try {
            sessionRepository.save(session);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public int joinSession(Long sessionID, Long userID) {
        Optional<Session> sessionByID = sessionRepository.findById(sessionID);
        if (sessionByID.isEmpty()) {
            logger.error("Session with ID " + sessionID + " doesn't exists");
            return -1;
        }
        Session session = sessionByID.get();

        setupSession(sessionID, session);

        if(Objects.equals(session.getUser1ID(), userID)) return 0;
        else if(Objects.equals(session.getUser2ID(), userID)) return 1;
        else return -1;

    }
    private void setupSession(Long sessionID, Session session) {
        Optional<Invitation> invitationOfSession = invitationRepository.findById(session.getInvitationID());
        Invitation invitation = invitationOfSession.orElseGet(Invitation::new);
        SessionManager.sessionCurrentMovieFilter.put(sessionID, SessionManager.InvitationFiltersToConsumerMovieFilter(invitation));

        SessionManager.sessionMovies.put(sessionID, new ArrayList<>());
        SessionManager.sessionCurrentMovieIndex.put(sessionID, new Integer[2]);
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[0] = 0;
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[1] = 0;

        SessionManager.sessionLikedMovieIndex.put(sessionID, new String[2]);
        SessionManager.sessionLikedMovieIndex.get(sessionID)[0] = "";
        SessionManager.sessionLikedMovieIndex.get(sessionID)[1] = "";

        SessionManager.sessionMatchCount.put(sessionID, 0);
        SessionManager.sessionMoviePage.put(sessionID, 1);
    }

    public boolean addMovies(Long sessionID) {
        Optional<Session> sessionByID = sessionRepository.findById(sessionID);
        if (sessionByID.isEmpty()) {
            logger.error("Session with ID " + sessionID + " doesn't exists");
            return false;
        }

        List<Movie> newMovies = theMovieDB.fetch(
                options -> options.setPage(SessionManager.sessionMoviePage.get(sessionID)),
                SessionManager.sessionCurrentMovieFilter.get(sessionID));
        SessionManager.sessionMovies.get(sessionID).addAll(newMovies);

        int currentPage = SessionManager.sessionMoviePage.get(sessionID);
        SessionManager.sessionMoviePage.put(sessionID, currentPage+1);
        return true;
    }

    public Movie getCurrentMovie(Long sessionID, Integer userNumber) {
        return SessionManager.sessionMovies.get(sessionID).get(SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber]);
    }

    public void increaseCurrentMovieIndex(Long sessionID, int userNumber) {
        SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber]++;
        if (SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber] >=
        SessionManager.sessionMovies.get(sessionID).size()-4) {
            boolean response = addMovies(sessionID);
            logger.info("Added new movies to list, success?: {}", response);
        }
    }

    public List<Movie> getCurrentListOfMovie(Long sessionID) {
        return SessionManager.sessionMovies.get(sessionID);
    }

    public Movie skipMovie(Long sessionID, int userNumber) {
        increaseCurrentMovieIndex(sessionID, userNumber);
        return getCurrentMovie(sessionID, userNumber);
    }
    public Movie likeMovie(Long sessionID, int userNumber) {
        Integer likedMovieIndex =
                SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber];

        if (SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber].length() == 0)
            SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber] += likedMovieIndex;
        else
            SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber] += " " + likedMovieIndex;

        increaseCurrentMovieIndex(sessionID, userNumber);
        return getCurrentMovie(sessionID, userNumber);
    }
    public Movie returnLastMovie(Long sessionID, int userNumber) {
        if (SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber] > 0) {
            SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber]--;

            int index = SessionManager.sessionCurrentMovieIndex.get(sessionID)[userNumber];

            String likedMovieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber];
            if(!Objects.equals(likedMovieIndexes, "")) {
                int[] indexes = Arrays.stream(likedMovieIndexes.split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                int lastLikedIndex = indexes[indexes.length - 1];

                if (index == lastLikedIndex) {
                    String newLikedMovies = Arrays.stream(indexes)
                            .limit(indexes.length - 1)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" "));
                    SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber] = newLikedMovies;
                }
            }
        }
        else logger.error("Cannot return last movie, because current movie index is 0");

        return getCurrentMovie(sessionID, userNumber);
    }
    public List<Movie> getLikedMovies(Long sessionID, int userNumber) {
        if (Objects.equals(SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber], ""))
            return new ArrayList<>();
        String movieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[userNumber];
        int[] indexes = Arrays.stream(movieIndexes.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        List<Movie> likedMovies = new ArrayList<>();
        for (int index : indexes) {
            likedMovies.add(SessionManager.sessionMovies.get(sessionID).get(index));
        }
        return likedMovies;
    }
    public List<Movie> getMatches(Long sessionID) {
        if (Objects.equals(SessionManager.sessionLikedMovieIndex.get(sessionID)[0], "") ||
                Objects.equals(SessionManager.sessionLikedMovieIndex.get(sessionID)[1], "")) {
            logger.error("No matches");
            return new ArrayList<>();
        }
        String movieIndexes;
        movieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[0];
        int[] User1Indexes = Arrays.stream(movieIndexes.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        movieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[1];
        int[] User2Indexes = Arrays.stream(movieIndexes.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] matchIndexed = Arrays.stream(User1Indexes)
                .filter(element -> Arrays.stream(User2Indexes).anyMatch(e -> e == element))
                .toArray();

        List<Movie> matchMovies = new ArrayList<>();
        for (int index : matchIndexed) {
            matchMovies.add(SessionManager.sessionMovies.get(sessionID).get(index));
        }
        return matchMovies;
    }
    public int getMatchCount(Long sessionID) {
        if (Objects.equals(SessionManager.sessionLikedMovieIndex.get(sessionID)[0], "") ||
                Objects.equals(SessionManager.sessionLikedMovieIndex.get(sessionID)[1], "")) {
            logger.error("No matches");
            return 0;
        }
        String movieIndexes;
        movieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[0];
        int[] User1Indexes = Arrays.stream(movieIndexes.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        movieIndexes = SessionManager.sessionLikedMovieIndex.get(sessionID)[1];
        int[] User2Indexes = Arrays.stream(movieIndexes.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        return (int) Arrays.stream(User1Indexes)
                .filter(element -> Arrays.stream(User2Indexes).anyMatch(e -> e == element))
                .count();
    }
    public boolean checkForNewMatch(Long sessionID) {
        int matchCount = getMatchCount(sessionID);
        if (matchCount > SessionManager.sessionMatchCount.get(sessionID)){
            SessionManager.sessionMatchCount.get(sessionID);
            SessionManager.sessionMatchCount.put(sessionID, matchCount);
            return true;
        }
        else return false;
    }
    public Movie getLastMatch(Long sessionID) {
        List<Movie> matches = getMatches(sessionID);
        if (matches.size() == 0) {
            logger.error("No matches");
            return null;
        }
        return matches.get(matches.size()-1);
    }
}
