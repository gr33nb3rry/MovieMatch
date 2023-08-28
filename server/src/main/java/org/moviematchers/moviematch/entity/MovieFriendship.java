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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id") //friendship initiator
    private UserEntity firstUserEntity;

    @ManyToOne
    @JoinColumn(name = "user2_id") //the one who gets befriended
    private UserEntity secondUserEntity;

    public MovieFriendship() {
    }

    public MovieFriendship(UserEntity firstUserEntity, UserEntity secondUserEntity) {
        this.firstUserEntity = firstUserEntity;
        this.secondUserEntity = secondUserEntity;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getFirstUserEntity(){
        return this.firstUserEntity;
    }

    public void setFirstUserEntity(UserEntity userEntity) {
        this.firstUserEntity = userEntity;
    }

    public UserEntity getSecondUserEntity(){
        return this.secondUserEntity;
    }

    public void setSecondUserEntity(UserEntity userEntity) {
        this.secondUserEntity = userEntity;
    }

}
