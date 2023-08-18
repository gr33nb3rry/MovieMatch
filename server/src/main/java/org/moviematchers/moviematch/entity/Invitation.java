package org.moviematchers.moviematch.entity;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;

@Entity
@Table(name = "user_invitation")
public class Invitation {
    @Id
    @SequenceGenerator(
            name = "invitation_sequence",
            sequenceName = "invitation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "invitation_sequence"
    )
    @Column(name = "invitation_id")
    private Long invitationID;
    @Column(name = "user_id_initiator")
    private Long userIDInitiator;
    @Column(name = "user_id_invited")
    private Long userIDInvited;
    @Column(name = "movie_genres")
    private MovieGenre[] movieGenres;
    @Column(name = "movie_date_start")
    private LocalDate movieDateStart;
    @Column(name = "movie_date_end")
    private LocalDate movieDateEnd;
    @Column(name = "movie_country")
    private CountryCode movieCountry;
    @Column(name = "is_movie_adult")
    private boolean isMovieAdult;

    public Invitation() {
    }

    public Invitation(Long invitationID, Long userIDInitiator, Long userIDInvited, MovieGenre[] movieGenres, LocalDate movieDateStart, LocalDate movieDateEnd, CountryCode movieCountry, boolean isMovieAdult) {
        this.invitationID = invitationID;
        this.userIDInitiator = userIDInitiator;
        this.userIDInvited = userIDInvited;
        this.movieGenres = movieGenres;
        this.movieDateStart = movieDateStart;
        this.movieDateEnd = movieDateEnd;
        this.movieCountry = movieCountry;
        this.isMovieAdult = isMovieAdult;
    }

    public Long getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(Long invitationID) {
        this.invitationID = invitationID;
    }

    public Long getUserIDInitiator() {
        return userIDInitiator;
    }

    public void setUserIDInitiator(Long userIDInitiator) {
        this.userIDInitiator = userIDInitiator;
    }

    public Long getUserIDInvited() {
        return userIDInvited;
    }

    public void setUserIDInvited(Long userIDInvited) {
        this.userIDInvited = userIDInvited;
    }

    public MovieGenre[] getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(MovieGenre[] movieGenres) {
        this.movieGenres = movieGenres;
    }

    public LocalDate getMovieDateStart() {
        return movieDateStart;
    }

    public void setMovieDateStart(LocalDate movieDateStart) {
        this.movieDateStart = movieDateStart;
    }

    public LocalDate getMovieDateEnd() {
        return movieDateEnd;
    }

    public void setMovieDateEnd(LocalDate movieDateEnd) {
        this.movieDateEnd = movieDateEnd;
    }

    public CountryCode getMovieCountry() {
        return movieCountry;
    }

    public void setMovieCountry(CountryCode movieCountry) {
        this.movieCountry = movieCountry;
    }

    public boolean isMovieAdult() {
        return isMovieAdult;
    }

    public void setMovieAdult(boolean movieAdult) {
        isMovieAdult = movieAdult;
    }
}
