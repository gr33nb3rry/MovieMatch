package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        UserEntity user = new UserEntity(
                "testName",
                "password");
        // when
        underTest.registerUser(user);
        // then
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());

        String password = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        UserEntity capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }
    @Test
    void cannotAddUser() {
        // given
        UserEntity user = new UserEntity(
                "testName",
                "password");
        doThrow(new RuntimeException("Some error")).when(userRepository).save(user);
        // when
        boolean result = underTest.registerUser(user);
        // then

        assertThat(result).isEqualTo(false);
    }

    @Test
    void canChangePassword() {
        // given
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";

        UserEntity user = new UserEntity("testName", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // when
        underTest.changeUserPassword(1L, newPassword);
        // then
        verify(userRepository).findById(1L);
        verify(bCryptPasswordEncoder).encode(newPassword);
    }
    @Test
    void cannotChangePassword() {
        // given
        String newPassword = "newPassword";
        doThrow(new RuntimeException("Some error")).when(userRepository).findById(1L);
        // when
        boolean result = underTest.changeUserPassword(1L, newPassword);
        // then
        assertThat(result).isEqualTo(false);
    }
    @Test
    void cannotChangePasswordBecauseOfBadPassword() {
        // given
        UserEntity user = new UserEntity("testName", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        boolean result = underTest.changeUserPassword(1L, null);
        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void canGetLoginUserIDByUsername() {
        // given
        String username = "testname";
        UserEntity user = new UserEntity(1L,username);
        when(userRepository.findByUserName(username)).thenReturn(user);

        // when
        Long result = underTest.getUserId(username);

        // then
        verify(userRepository).findByUserName(username);
        assertThat(result).isEqualTo(user.getId());
    }

    @Test
    void canGetUserNameByID() {
        // given
        Long userId = 1L;
        String username = "testname";
        UserEntity user = new UserEntity(userId,username);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        String result = underTest.getUserUsername(userId);

        // then
        verify(userRepository).findById(userId);
        assertThat(result).isEqualTo(user.getUsername());
    }
}