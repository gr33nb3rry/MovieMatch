package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<MovieUser> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("getLoginID")
    public Long getLoginUserID(String username) {
        return userService.getLoginUserID(username);
    }

    @GetMapping("name")
    public String getUserNameByID(@RequestParam Long id) {
        return userService.getUserNameByID(id);
    }

    @PostMapping(path = "register")
    public ResponseEntity<String> addUser(@RequestBody MovieUser movieUser) {
        boolean result = userService.addUser(movieUser);
        if (result) {
            return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("password")
    public ResponseEntity<String> changePassword(
            @RequestParam Long id,
            @RequestParam String value) {
        boolean result = userService.changePassword(id, value);
        if (result) {
            return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to change password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
