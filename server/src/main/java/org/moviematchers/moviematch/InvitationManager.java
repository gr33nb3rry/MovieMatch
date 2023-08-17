package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.MovieFilter;
import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;
import java.util.function.Consumer;

public class InvitationManager {
    public static void InvitationFiltersToConsumerMovieFilter(Invitation invitation) {

        Consumer<MovieFilter> customFilterConsumer = (movieFilter) -> {
            movieFilter.setGenres(invitation.getMovieGenres());

            movieFilter.setReleaseDateStart(invitation.getMovieDateStart());
            movieFilter.setReleaseDateEnd(invitation.getMovieDateEnd());

            movieFilter.setOriginCountry(invitation.getMovieCountry());

            movieFilter.setAdultRated(invitation.isMovieAdult());

        };
    }
}
