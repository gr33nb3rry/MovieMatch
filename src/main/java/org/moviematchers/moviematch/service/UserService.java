package org.moviematchers.moviematch.service;

import jakarta.transaction.Transactional;
import org.moviematchers.moviematch.entity.User;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }
    public boolean login(User user) {
        User userFindByUserName = userRepository.findByUserName(user.getUserName());
        if (userFindByUserName != null){
            //System.out.println(userFindByUserName.getUserName());
            return user.getUserPassword().equals(userFindByUserName.getUserPassword());
        }
        return false;
    }

    @Transactional
    public void changeUsername(Long id, String value) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(
                        "User with ID " + id + " is not found"
                ));
        if (value != null && value.length() > 0 && !Objects.equals(user.getUserPassword(), value)) {
            user.setUserName(value);
        }
        else {
            throw new IllegalStateException("Inappropriate username");
        }
    }
    @Transactional
    public void changePassword(Long id, String value) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(
                        "User with ID " + id + " is not found"
                ));
        if (value != null && value.length() > 0 && !Objects.equals(user.getUserPassword(), value)) {
            user.setUserPassword(value);
        }
        else {
            throw new IllegalStateException(
                    "Inappropriate password"
            );
        }
    }
}
