package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "register")
    public void registerUser(@RequestBody UserCredentials credentials) {
		service.registerUser(credentials);
    }

    @PatchMapping("password")
    public void changePassword(@RequestBody User user) {
        this.service.changeUserPassword(user);
    }

}
