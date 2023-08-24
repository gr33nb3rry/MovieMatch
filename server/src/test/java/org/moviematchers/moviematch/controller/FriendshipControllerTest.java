package org.moviematchers.moviematch.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.moviematchers.moviematch.service.FriendshipService;

import static org.junit.jupiter.api.Assertions.*;
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
        MovieUser user1 = new MovieUser(1L, "name1");
        MovieUser user2 = new MovieUser(2L, "name2");
        MovieFriendship collection = new MovieFriendship(user1, user2);
        // when
        underTest.addFriendship(collection);
        // then
        verify(service).addFriendship(collection);
    }
}