package org.moviematchers.moviematch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_movie_collection")
public class UserMovieCollection {
    @Id
    @SequenceGenerator(
            name = "collection_sequence",
            sequenceName = "collection_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "collection_sequence"
    )
    @Column(name = "collection_id")
    private Long collectionID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MovieUser userID;
    @Column(name = "movie_title")
    private String movieTitle;
    @Column(name = "user_rating")
    private double userRating;

    public UserMovieCollection() {
    }
/*
    public UserMovieCollection(Long userID, String movieTitle, double userRating) {
        this.userID = userID;
        this.movieTitle = movieTitle;
        this.userRating = userRating;
    }
*/
    public UserMovieCollection(MovieUser userID, String movieTitle, double userRating) {
        this.userID = userID;
        this.movieTitle = movieTitle;
        this.userRating = userRating;
    }
    public Long getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(Long collectionID) {
        this.collectionID = collectionID;
    }
/*
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
*/

    public MovieUser getUserID() {
        return userID;
    }

    public void setUserID(MovieUser userID) {
        this.userID = userID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
