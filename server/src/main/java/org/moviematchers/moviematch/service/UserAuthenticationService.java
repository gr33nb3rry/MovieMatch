package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.repository.UserRepository;
import org.moviematchers.moviematch.validation.ValidUsername;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(@ValidUsername String username) throws UsernameNotFoundException {
        UserEntity movieUser = userRepository.findByUsername(username);
        if (movieUser == null) {
            throw new UsernameNotFoundException("user not found");
        }
		return new User(movieUser.getUsername(), movieUser.getPassword(), Collections.emptyList());
    }
}
