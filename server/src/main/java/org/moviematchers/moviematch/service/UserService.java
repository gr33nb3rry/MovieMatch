package org.moviematchers.moviematch.service;

import jakarta.transaction.Transactional;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public List<MovieUser> getUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(MovieUser movieUser) {
        try {
            String password = movieUser.getUserPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            movieUser.setUserPassword(encodedPassword);

            userRepository.save(movieUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Transactional
    public boolean changePassword(Long id, String value) {

        try {
            MovieUser movieUser = userRepository.findById(id).
                    orElseThrow(() -> new IllegalStateException(
                            "User with ID " + id + " is not found"
                    ));
            if (value != null && value.length() > 0 && !Objects.equals(movieUser.getUserPassword(), value)) {
                movieUser.setUserPassword(bCryptPasswordEncoder.encode(value));
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e) {
            return false;
        }
    }

    public Long getLoginUserID(String username) {
        MovieUser user = userRepository.findByUserName(username);
        return user.getUserID();
    }
    public String getUserNameByID(Long id) {
        MovieUser user =  userRepository.findById(id).orElseGet(MovieUser::new);
        return user.getUserName();
    }
}
