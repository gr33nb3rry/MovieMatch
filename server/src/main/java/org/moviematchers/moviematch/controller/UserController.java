package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "register")
    public void addUser(@RequestBody MovieUser movieUser) {
        userService.addUser(movieUser);
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
