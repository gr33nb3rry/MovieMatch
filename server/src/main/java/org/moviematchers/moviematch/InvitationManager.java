package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.MovieFilter;
import org.moviematchers.moviematch.entity.Invitation;
import java.util.function.Consumer;

public class InvitationManager {
    public static Consumer<MovieFilter> currentMovieFilter;
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
