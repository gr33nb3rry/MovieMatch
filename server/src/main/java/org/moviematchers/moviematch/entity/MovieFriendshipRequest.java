package org.moviematchers.moviematch.entity;

public class MovieFriendshipRequest {
    private Long user1ID;
    private Long user2ID;

    public Long getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(Long user1ID) {
        this.user1ID = user1ID;
    }

    public Long getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(Long user2ID) {
        this.user2ID = user2ID;
    }
}
