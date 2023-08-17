package org.moviematchers.moviematch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.repository.FriendshipRepository;
import org.moviematchers.moviematch.entity.MovieUser;

import java.util.List;
import java.util.Objects;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }
    //get friendships for a specific user
    public List<MovieFriendship> getFriendshipsForUser(MovieUser user) {
        return friendshipRepository.findByUser1IDOrUser2ID(user, user);
    }
    //create new friendship


    public List<MovieFriendship> getFriendships() {
        return friendshipRepository.findAll();
    }

    public void sendFriendshipRequest(MovieFriendship friendship) {
        Long user1ID = friendship.getUser1ID().getUserID();
        Long user2ID = friendship.getUser2ID().getUserID();
        if (!Objects.equals(user1ID, user2ID)){
            friendshipRepository.save(friendship);
        }
    }
}
