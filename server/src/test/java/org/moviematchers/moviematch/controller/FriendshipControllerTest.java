package org.moviematchers.moviematch.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.service.FriendshipService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FriendshipControllerTest {
    @Mock
    private FriendshipService service;
    private FriendshipController underTest;

    @BeforeEach
    void setUp() {
        underTest = new FriendshipController(service);
    }

    @Test
    void getFriendshipsForUser() {
        // given
        Long userId = 1L;
        // when
        underTest.getFriendshipsForUser(userId);
        // then
        verify(service).getFriendshipsForUser(userId);
    }

    @Test
    void addFriendship() {
        // given
        UserEntity user1 = new UserEntity(1L, "name1");
        UserEntity user2 = new UserEntity(2L, "name2");
        MovieFriendship friendship = new MovieFriendship(user1, user2);
        // when
        underTest.addFriendship(friendship);
        // then
        verify(service).addFriendship(friendship);
    }
}