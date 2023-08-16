package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailAuthService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MovieUser movieUser = userRepository.findByUserName(username);
        if (movieUser != null){
            User user = new User(movieUser.getUserName(), movieUser.getUserPassword(), Collections.emptyList());
            return user;
        }
        else {
            throw new UsernameNotFoundException("user is not found");
        }
    }
}
