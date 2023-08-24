package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canAddUser() {
        // given
        MovieUser user = new MovieUser(
                "testName",
                "password");
        // when
        underTest.addUser(user);
        // then
        ArgumentCaptor<MovieUser> argumentCaptor = ArgumentCaptor.forClass(MovieUser.class);
        verify(userRepository).save(argumentCaptor.capture());

        String password = user.getUserPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setUserPassword(encodedPassword);

        MovieUser capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canChangePassword() {
        // given
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";

        MovieUser user = new MovieUser("testName", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // when
        underTest.changePassword(1L, newPassword);
        // then
        verify(userRepository).findById(1L);
        verify(bCryptPasswordEncoder).encode(newPassword);
    }

    @Test
    void canGetLoginUserIDByUsername() {
        // given
        String username = "testname";
        MovieUser user = new MovieUser(1L,username);
        when(userRepository.findByUserName(username)).thenReturn(user);

        // when
        Long result = underTest.getLoginUserID(username);

        // then
        verify(userRepository).findByUserName(username);
        assertThat(result).isEqualTo(user.getUserID());
    }

    @Test
    void canGetUserNameByID() {
        // given
        Long userId = 1L;
        String username = "testname";
        MovieUser user = new MovieUser(userId,username);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        String result = underTest.getUserNameByID(userId);

        // then
        verify(userRepository).findById(userId);
        assertThat(result).isEqualTo(user.getUserName());
    }
}