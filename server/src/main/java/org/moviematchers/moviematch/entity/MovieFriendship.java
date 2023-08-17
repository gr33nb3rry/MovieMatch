package org.moviematchers.moviematch.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_friendship")
public class MovieFriendship {
    @Id
    @SequenceGenerator(
            name = "friendship_sequence",
            sequenceName = "friendship_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friendship_sequence"
    )
    @Column(name = "friendship_id")
    private Long friendshipID;
    @ManyToOne
    @JoinColumn(name = "user1_id") //friendship initiator
    private MovieUser user1ID;
    @ManyToOne
    @JoinColumn(name = "user2_id") //the one who gets befriended
    private MovieUser user2ID;
    public MovieFriendship() {
    }
    public MovieFriendship(MovieUser user1ID, MovieUser user2ID) {
        this.user1ID = user1ID;
        this.user2ID = user2ID;
    }
    public Long getFriendshipID() {
        return friendshipID;
    }
    public void setFriendshipID(Long friendshipID) {
        this.friendshipID = friendshipID;
    }
    public MovieUser getUser1ID(){
        return user1ID;
    }
    public void setUser1ID(MovieUser user1ID) {
        this.user1ID = user1ID;
    }
    public MovieUser getUser2ID(){
        return user2ID;
    }
    public void setUser2ID(MovieUser user2ID) {
        this.user2ID = user2ID;
    }

}
