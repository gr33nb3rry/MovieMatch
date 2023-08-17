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

    @GetMapping("all")
    public List<MovieFriendship> getAllFriendships() {
        return friendshipService.getAllFriendships();
    }
    @GetMapping("byID")
    public List<MovieFriendship> getFriendshipsForUser(@RequestParam Long id) {

        return friendshipService.getFriendshipsForUser(id);
    }

    @PostMapping("request")
    public void addFriendship(@RequestBody MovieFriendship friendship) {

        friendshipService.addFriendship(friendship);
    }
}
