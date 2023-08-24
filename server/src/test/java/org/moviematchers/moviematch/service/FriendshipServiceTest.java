package org.moviematchers.moviematch.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.repository.FriendshipRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    void canGetFriendshipsForUser() {
        // given
        Long userId = 1L;
        List<MovieFriendship> list1 = new ArrayList<>();
        List<MovieFriendship> list2 = new ArrayList<>();

        list1.add(new MovieFriendship(new MovieUser(userId, "User1"), new MovieUser(2L, "User2")));
        list2.add(new MovieFriendship(new MovieUser(3L, "User3"), new MovieUser(userId, "User1")));
        when(friendshipRepository.findByUser1IDUserID(userId)).thenReturn(list1);
        // when
        List<MovieFriendship> result = underTest.getFriendshipsForUser(userId);
        // then
        verify(friendshipRepository).findByUser1IDUserID(userId);
        verify(friendshipRepository).findByUser2IDUserID(userId);

        assertThat(result).containsExactlyInAnyOrderElementsOf(list1);
        assertThat(result).doesNotContainAnyElementsOf(list2);
    }

    @Test
    void canAddFriendship() {
        // given
        MovieUser user1 = new MovieUser(1L, "name1");
        MovieUser user2 = new MovieUser(2L, "name2");
        MovieFriendship friendship = new MovieFriendship(user1, user2);
        // when
        underTest.addFriendship(friendship);
        // then
        ArgumentCaptor<MovieFriendship> argumentCaptor = ArgumentCaptor.forClass(MovieFriendship.class);
        verify(friendshipRepository).save(argumentCaptor.capture());

        MovieFriendship captured = argumentCaptor.getValue();
        AssertionsForClassTypes.assertThat(captured).isEqualTo(friendship);
    }
    @Test
    void cannotAddFriendshipBecauseTheSameUsers() {
        // given
        MovieUser user1 = new MovieUser(1L, "name1");
        MovieFriendship friendship = new MovieFriendship(user1, user1);

        // when
        boolean result = underTest.addFriendship(friendship);
        // then
        assertThat(result).isEqualTo(false);
    }
    @Test
    void cannotAddFriendship() {
        // given
        MovieUser user1 = new MovieUser(1L, "name1");
        MovieUser user2 = new MovieUser(2L, "name2");
        MovieFriendship friendship = new MovieFriendship(user1, user2);
        doThrow(new RuntimeException("Some error")).when(friendshipRepository).save(friendship);
        // when
        boolean result = underTest.addFriendship(friendship);
        // then
        assertThat(result).isEqualTo(false);
    }
}