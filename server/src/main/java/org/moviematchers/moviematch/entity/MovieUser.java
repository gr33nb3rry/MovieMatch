package org.moviematchers.moviematch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movie_match_user")
public class MovieUser {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "user_id")
    private Long userID;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    //@JsonManagedReference
    @OneToMany(mappedBy = "userID")
    @JsonIgnore
    private List<UserMovieCollection> collections;

    public MovieUser() {
    }


    public MovieUser(Long userID, String userName, String userPassword, List<UserMovieCollection> collections) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.collections = collections;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<UserMovieCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<UserMovieCollection> collections) {
        this.collections = collections;
    }
}
