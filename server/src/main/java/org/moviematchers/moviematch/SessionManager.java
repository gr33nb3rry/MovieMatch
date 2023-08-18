package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieFilter;
import org.moviematchers.moviematch.entity.Invitation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SessionManager {

    //public static List<Integer> userLikedMovieIndex = new ArrayList<>();
    //public static int currentMovieIndex;

    public static Map<Long, List<Movie>> sessionMovies = new LinkedHashMap<>();
    public static Map<Long, Consumer<MovieFilter>> sessionCurrentMovieFilter = new LinkedHashMap<>();
    public static Map<Long, Integer[]> sessionCurrentMovieIndex = new LinkedHashMap<>();
    public static Map<Long, String[]> sessionLikedMovieIndex = new LinkedHashMap<>();
    public static Consumer<MovieFilter> InvitationFiltersToConsumerMovieFilter(Invitation invitation) {
        return (movieFilter) -> {
            movieFilter.setGenres(invitation.getMovieGenres());
            movieFilter.setReleaseDateStart(invitation.getMovieDateStart());
            movieFilter.setReleaseDateEnd(invitation.getMovieDateEnd());
            movieFilter.setOriginCountry(invitation.getMovieCountry());
            movieFilter.setAdultRated(invitation.isMovieAdult());
        };
    }
}
