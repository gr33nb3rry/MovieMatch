package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public Long getLoginUserID(Principal principal) {
        return userService.getLoginUserID(principal.getName());
    }

    @GetMapping("name")
    public String getUserNameByID(@RequestParam Long id) {
        return userService.getUserNameByID(id);
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
