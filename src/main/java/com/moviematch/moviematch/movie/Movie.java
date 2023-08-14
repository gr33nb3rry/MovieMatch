package com.moviematch.moviematch.movie;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Movie {
    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    private Long ID;
    private String imdbID;
    private LocalDate watchedDate;
    float hisRating;
    float herRating;
    private String type;
    @Transient
    float averageRating;


    public Movie() {}

    public Movie(Long ID, String imdbID, LocalDate watchedDate, float hisRating, float herRating, float averageRating, String type) {
        this.ID = ID;
        this.imdbID = imdbID;
        this.watchedDate = watchedDate;
        this.hisRating = hisRating;
        this.herRating = herRating;
        this.averageRating = averageRating;
        this.type = type;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public LocalDate getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(LocalDate watchedDate) {
        this.watchedDate = watchedDate;
    }

    public float getHisRating() {
        return hisRating;
    }

    public void setHisRating(float hisRating) {
        this.hisRating = hisRating;
    }

    public float getHerRating() {
        return herRating;
    }

    public void setHerRating(float herRating) {
        this.herRating = herRating;
    }

    public float getAverageRating() {
        return (hisRating + herRating) / 2;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
