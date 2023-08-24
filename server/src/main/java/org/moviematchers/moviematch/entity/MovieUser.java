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

    @OneToMany(mappedBy = "user1ID")
    @JsonIgnore
    private List<MovieFriendship> friendshipsInitiator;
    @OneToMany(mappedBy = "user2ID")
    @JsonIgnore
    private List<MovieFriendship> friendshipsBefriended;

    public MovieUser() {
    }


    public MovieUser(Long userID, String userName, String userPassword, List<UserMovieCollection> collections, List<MovieFriendship> friendshipsInitiator, List<MovieFriendship> friendshipsBefriended) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.collections = collections;
        this.friendshipsInitiator = friendshipsInitiator;
        this.friendshipsBefriended = friendshipsBefriended;
    }
    public MovieUser(Long userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
    public MovieUser(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
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

    public List<MovieFriendship> getFriendshipsInitiator() {
        return friendshipsInitiator;
    }

    public void setFriendshipsInitiator(List<MovieFriendship> friendshipsInitiator) {
        this.friendshipsInitiator = friendshipsInitiator;
    }

    public List<MovieFriendship> getFriendshipsBefriended() {
        return friendshipsBefriended;
    }

    public void setFriendshipsBefriended(List<MovieFriendship> friendshipsBefriended) {
        this.friendshipsBefriended = friendshipsBefriended;
    }
}
