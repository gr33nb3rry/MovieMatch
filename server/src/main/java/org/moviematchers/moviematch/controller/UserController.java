package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    public List<MovieUser> getUsers(Authentication auth) {
        User user = (User)auth.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return userService.getUsers();
    }
    @GetMapping("getLoginID")
    public Long getLoginUserID(Authentication auth) {
        User user = (User)auth.getPrincipal();
        String username = user.getUsername();
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
            return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    @PostMapping(path = "login")
    public String login(@RequestBody MovieUser movieUser) {
        boolean response = userService.login(movieUser);

        if (response)
            return "Successfully logged in";
        else return "Username or password is incorrect";
    }
*/

    @PutMapping("username")
    public ResponseEntity<String> changeUsername(
            @RequestParam Long id,
            @RequestParam String value) {
        boolean result = userService.changeUsername(id, value);
        if (result) {
            return new ResponseEntity<>("Username successfully changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to change username", HttpStatus.INTERNAL_SERVER_ERROR);
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
