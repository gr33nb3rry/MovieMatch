package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.repository.FriendshipRepository;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {
    @Mock
    private FriendshipRepository friendshipRepository;
    private FriendshipService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FriendshipService(friendshipRepository);
    }

    @Test
    void getFriendshipsForUser() {
    }

    @Test
    void getAllFriendships() {
    }

    @Test
    void addFriendship() {
    }
}