package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.MovieMatchUser;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    public List<MovieMatchUser> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(MovieMatchUser user) {
        userRepository.save(user);
    }
}
