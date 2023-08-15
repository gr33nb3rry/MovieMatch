package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.MovieMatchUser;
import org.moviematchers.moviematch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<MovieMatchUser> getUsers() {return userService.getUsers();}
    @PostMapping
    public void addUser(@RequestBody MovieMatchUser user) {
        userService.addUser(user);
    }
}
