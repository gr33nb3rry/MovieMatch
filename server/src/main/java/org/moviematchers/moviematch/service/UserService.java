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

    public void addUser(MovieUser movieUser) {
        String password = movieUser.getUserPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        movieUser.setUserPassword(encodedPassword);

        userRepository.save(movieUser);
    }
    /*
    public boolean login(MovieUser movieUser) {
        MovieUser userFindByMovieUserName = userRepository.findByUserName(movieUser.getUserName());
        if (userFindByMovieUserName != null){
            //System.out.println(userFindByUserName.getUserName());
            //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            String expectedPassword = movieUser.getUserPassword();
            String realPassword = userFindByMovieUserName.getUserPassword();
            String encodedExpectedPassword = bCryptPasswordEncoder.encode(expectedPassword);

            System.out.println(encodedExpectedPassword);
            System.out.println(realPassword);

            //return encodedExpectedPassword.equals(realPassword);
            return bCryptPasswordEncoder.matches(expectedPassword, realPassword);
        }
        return false;
    }
*/
    @Transactional
    public void changeUsername(Long id, String value) {
        MovieUser movieUser = userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(
                        "User with ID " + id + " is not found"
                ));
        if (value != null && value.length() > 0 && !Objects.equals(movieUser.getUserPassword(), value)) {
            movieUser.setUserName(value);
        }
        else {
            throw new IllegalStateException("Inappropriate username");
        }
    }
    @Transactional
    public void changePassword(Long id, String value) {
        MovieUser movieUser = userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(
                        "User with ID " + id + " is not found"
                ));
        if (value != null && value.length() > 0 && !Objects.equals(movieUser.getUserPassword(), value)) {
            movieUser.setUserPassword(bCryptPasswordEncoder.encode(value));
        }
        else {
            throw new IllegalStateException(
                    "Inappropriate password"
            );
        }
    }

    public Long getLoginUserID(String username) {
        MovieUser user = userRepository.findByUserName(username);
        return user.getUserID();
    }
}
