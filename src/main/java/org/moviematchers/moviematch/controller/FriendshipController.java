package org.moviematchers.moviematch.controller;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.MovieFriendshipRequest;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.service.FriendshipService;


@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final UserRepository userRepository;
    @Autowired
    public FriendshipController(FriendshipService friendshipService, UserRepository userRepository) {
        this.friendshipService = friendshipService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<MovieFriendship> getFriendships() {
        return friendshipService.getFriendships();
    }
    // Endpoint to accept a friendship request
    /*@PostMapping("/accept")
    public ResponseEntity<String> acceptFriendship(@RequestBody FriendshipRequest friendshipRequest) {
        MovieUser user1 = // Get user1 from UserRepository or wherever you store user data
                MovieUser user2 = // Get user2 from UserRepository or wherever you store user data
        friendshipService.acceptFriendship(user1, user2);
        return ResponseEntity.ok("Friendship accepted");
    }

    // Endpoint to decline a friendship request
    @PostMapping("/decline")
    public ResponseEntity<String> declineFriendship(@RequestParam Long friendshipId) {
        friendshipService.declineFriendship(friendshipId);
        return ResponseEntity.ok("Friendship declined");
    }*/
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendship(@RequestBody MovieFriendshipRequest friendshipRequest) {
        // Fetch user1 and user2 using their IDs from the request
        MovieUser user1 = userRepository.findById(friendshipRequest.getUser1ID())
                .orElseThrow(() -> new IllegalArgumentException("User1 not found"));

        MovieUser user2 = userRepository.findById(friendshipRequest.getUser2ID())
                .orElseThrow(() -> new IllegalArgumentException("User2 not found"));

        friendshipService.createFriendship(user1, user2);
        return ResponseEntity.ok("Friendship accepted");
    }

    @PostMapping("/decline")
    public ResponseEntity<String> declineFriendship(@RequestBody MovieFriendshipRequest friendshipRequest) {
        MovieUser user1 = userRepository.findById(friendshipRequest.getUser1ID())
                .orElseThrow(() -> new IllegalArgumentException("User1 not found"));

        MovieUser user2 = userRepository.findById(friendshipRequest.getUser2ID())
                .orElseThrow(() -> new IllegalArgumentException("User2 not found"));

        friendshipService.declineFriendship(user1, user2);
        return ResponseEntity.ok("Friendship declined");
    }
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendshipRequest(@RequestBody MovieFriendshipRequest friendshipRequest) {
        MovieUser user1 = userRepository.findById(friendshipRequest.getUser1ID())
                .orElseThrow(() -> new IllegalArgumentException("User1 not found"));

        MovieUser user2 = userRepository.findById(friendshipRequest.getUser2ID())
                .orElseThrow(() -> new IllegalArgumentException("User2 not found"));

        friendshipService.sendFriendshipRequest(user1, user2);
        return ResponseEntity.ok("Friendship request sent");
    }
}
