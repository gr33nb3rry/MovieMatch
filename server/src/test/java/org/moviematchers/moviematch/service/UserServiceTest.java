package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.dto.UserCredentialsImpl;
import org.moviematchers.moviematch.dto.UserImpl;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.mapper.Mapper;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private Mapper<UserEntity, User> mapperUser;
    @Mock
    private Mapper<UserCredentials, UserEntity> mapperCred;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, encoder, mapperUser, mapperCred);
    }

    @Test
    void canGetUserId() {
        // when
        underTest.getUserId("username");
        // then
        verify(userRepository).findByUsernameIgnoreCase("username");
    }

    @Test
    void canAddUser() {
        // given
        UserCredentialsImpl user = new UserCredentialsImpl(
                "testName",
                "password");
        UserEntity userEntity = new UserEntity("testName", "password");
        when(mapperCred.map(user)).thenReturn(userEntity);
        // when
        underTest.registerUser(user);

        // then
        verify(userRepository).save(userEntity);
    }
    @Test
    void cannotAddUser() {
        // given
        UserCredentialsImpl user = new UserCredentialsImpl(
                "testName",
                "password");
        UserEntity userEntity = new UserEntity("testName", "password");
        when(mapperCred.map(user)).thenReturn(userEntity);
        doThrow(new RuntimeException("Some error")).when(userRepository).save(any());
        // when
        try {
            underTest.registerUser(user);
        } catch (RuntimeException e) {
            assertEquals("Some error", e.getMessage());
        }
    }

    @Test
    void canChangePassword() {
        // given
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";

        UserCredentialsImpl cred = new UserCredentialsImpl("testName", newPassword);
        UserImpl user = new UserImpl(1L, cred);
        UserEntity userEntity = new UserEntity("testName", "password");
        when(userRepository.getReferenceById(1L)).thenReturn(userEntity);
        when(encoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // when
        underTest.changeUserPassword(user);
        // then
        verify(userRepository).getReferenceById(1L);
        verify(encoder).encode(newPassword);
    }
    @Test
    void cannotChangePassword() {
        // given
        String newPassword = "newPassword";
        UserCredentialsImpl cred = new UserCredentialsImpl("testName", "password");
        UserImpl user = new UserImpl(1L, cred);
        doThrow(new RuntimeException("Some error")).when(userRepository).getReferenceById(user.getId());
        // when
        try {
            underTest.changeUserPassword(user);
        } catch (RuntimeException e) {
            assertEquals("Some error", e.getMessage());
        }
    }

    @Test
    void canGetLoginUserIDByUsername() {
        // given
        String username = "testname";
        UserEntity user = new UserEntity(1L,username);
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(user);

        // when
        Long result = underTest.getUserId(username);

        // then
        verify(userRepository).findByUsernameIgnoreCase(username);
        assertThat(result).isEqualTo(user.getId());
    }

}