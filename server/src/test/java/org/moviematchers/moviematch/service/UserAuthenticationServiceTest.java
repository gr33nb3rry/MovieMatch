package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserAuthenticationService underTest;
    @BeforeEach
    void setUp() {
        underTest = new UserAuthenticationService(userRepository);
    }

    @Test
    void loadUserByUsername() {
        // given
        String username = "admin";
        UserEntity movieUser = new UserEntity(username, "password");
        User user = new User(movieUser.getUsername(), movieUser.getPassword(), Collections.emptyList());
        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(movieUser);
        // when
        UserDetails result = underTest.loadUserByUsername(username);
        // then
        verify(userRepository).findByUsernameIgnoreCase(username);
        assertThat(result).isEqualTo(user);
    }
}