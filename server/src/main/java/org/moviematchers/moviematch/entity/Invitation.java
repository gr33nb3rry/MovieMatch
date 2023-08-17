package org.moviematchers.moviematch.entity;

import jakarta.persistence.*;

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
    private String movieGenres; // TheMovieDBFetchStrategy.MOVIE_GENRE_TOKENS tokens with space separator   ex: "35 80 18"
    @Column(name = "movie_date_start")
    private LocalDate movieDateStart;
    @Column(name = "movie_date_end")
    private LocalDate movieDateEnd;
    @Column(name = "movie_country")
    private String movieCountry; // CountryCode.alpha3   ex: LVA for Latvia
    @Column(name = "is_movie_adult")
    private boolean isMovieAdult;

    public Invitation() {
    }

    public Invitation(Long invitationID, Long userIDInitiator, Long userIDInvited, String movieGenres, LocalDate movieDateStart, LocalDate movieDateEnd, String movieCountry, boolean isMovieAdult) {
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

    public String getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(String movieGenres) {
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

    public String getMovieCountry() {
        return movieCountry;
    }

    public void setMovieCountry(String movieCountry) {
        this.movieCountry = movieCountry;
    }

    public boolean isMovieAdult() {
        return isMovieAdult;
    }

    public void setMovieAdult(boolean movieAdult) {
        isMovieAdult = movieAdult;
    }
}
