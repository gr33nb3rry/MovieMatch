package org.moviematchers.moviematch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.repository.FriendshipRepository;
import org.moviematchers.moviematch.entity.MovieUser;

import java.util.List;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }
    //get friendships for a specific user
    public List<MovieFriendship> getFriendshipsForUser(MovieUser user) {
        return friendshipRepository.findByUser1OrUser2(user, user);
    }
    //create new friendship
    public void createFriendship(MovieUser user1, MovieUser user2) {
        MovieFriendship friendship = new MovieFriendship(user1, user2, "Accepted");
        friendshipRepository.save(friendship);
    }
    //accept a friendship
   /* public MovieFriendship acceptFriendship(MovieUser user1, MovieUser user2) {
        MovieFriendship friendship = friendshipRepository.findByUser1AndUser2(user1, user2)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        // Update the friendship status to "Accepted"
        friendship.setStatus("Accepted");
        return friendshipRepository.save(friendship);
    } */
    //decline a friendship
    public void declineFriendship(MovieUser user1, MovieUser user2) {
        MovieFriendship friendship = friendshipRepository.findByUser1AndUser2(user1, user2)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

        // Update the friendship status to "Declined"
        MovieFriendship declinedFriendship = new MovieFriendship(user1, user2, "Declined");
        friendshipRepository.save(declinedFriendship);
    }

    public List<MovieFriendship> getFriendships() {
        return friendshipRepository.findAll();
    }

    public void sendFriendshipRequest(MovieUser user1, MovieUser user2) {
        MovieFriendship friendshipRequest = new MovieFriendship();
        friendshipRequest.setUser1(user1);
        friendshipRequest.setUser2(user2);
        friendshipRequest.setStatus("Pending");
        friendshipRepository.save(friendshipRequest);
    }
}
