package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.User;
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
    public List<User> getUsers() {return userService.getUsers();}
    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }
    @PutMapping("username")
    public void changeUsername(
            @RequestParam Long id,
            @RequestParam String value) {
        userService.changeUsername(id, value);
    }
    @PutMapping("password")
    public void changePassword(
            @RequestParam Long id,
            @RequestParam String value) {
        userService.changePassword(id, value);
    }

}
