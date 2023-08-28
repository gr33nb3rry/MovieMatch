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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "user_rating")
    private double userRating;

    public UserMovieCollection() {
    }

    public UserMovieCollection(UserEntity userEntity, String movieTitle, double userRating) {
        this.userEntity = userEntity;
        this.movieTitle = movieTitle;
        this.userRating = userRating;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
