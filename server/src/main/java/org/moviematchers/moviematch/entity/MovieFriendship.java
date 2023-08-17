package org.moviematchers.moviematch.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_friendship")
public class MovieFriendship {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "friendship_sequence",
            sequenceName = "friendship_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friendship_sequence"
    )
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "user1ID") //friendship initiator
    private MovieUser user1;
    @ManyToOne
    @JoinColumn(name = "user2ID") //the one who gets befriended
    private MovieUser user2;
    public MovieFriendship() {
    }
    public MovieFriendship(MovieUser user1, MovieUser user2, String status) {
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public MovieUser getUser1(){
        return user1;
    }
    public void setUser1(MovieUser user1) {
        this.user1 = user1;
    }
    public MovieUser getUser2(){
        return user2;
    }
    public void setUser2(MovieUser user2) {
        this.user2= user2;
    }

}
