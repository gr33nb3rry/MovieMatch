package org.moviematchers.moviematch.controller;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.moviematchers.moviematch.service.FriendshipService;


@RestController
@RequestMapping(path = "friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
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

    @PostMapping("/request")
    public void sendFriendshipRequest(@RequestBody MovieFriendship friendship) {

        friendshipService.sendFriendshipRequest(friendship);
    }
}
