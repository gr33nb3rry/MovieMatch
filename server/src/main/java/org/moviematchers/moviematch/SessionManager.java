package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieFilter;
import org.moviematchers.moviematch.entity.Invitation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SessionManager {
    public static Consumer<MovieFilter> currentMovieFilter;
    public static List<Movie> movies = new ArrayList<>();
    public static List<Integer> userLikedMovieIndex = new ArrayList<>();
    public static int currentMovieIndex;
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
