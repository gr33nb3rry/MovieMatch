package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.MovieUser;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class FriendshipRepositoryTest {
    @Autowired
    private FriendshipRepository underTest;
    @Autowired
    private UserRepository userUnderTest;

    @Test
    void shouldReturnListOfFriendshipsByUser1ID() {
        MovieUser user1 = new MovieUser();
        MovieUser user2 = new MovieUser();
        userUnderTest.save(user1);
        userUnderTest.save(user2);

        MovieFriendship friendship = new MovieFriendship(user1, user2);
        underTest.save(friendship);

        List<MovieFriendship> result = underTest.findByUser1IDUserID(user1.getUserID());

        assertThat(result.get(0).getUser1ID().getUserID()).isEqualTo(user1.getUserID());
    }

    @Test
    void shouldReturnListOfFriendshipsByUser2ID() {
        MovieUser user1 = new MovieUser();
        MovieUser user2 = new MovieUser();
        userUnderTest.save(user1);
        userUnderTest.save(user2);

        MovieFriendship friendship = new MovieFriendship(user1, user2);
        underTest.save(friendship);

        List<MovieFriendship> result = underTest.findByUser2IDUserID(user2.getUserID());

        assertThat(result.get(0).getUser2ID().getUserID()).isEqualTo(user2.getUserID());
    }
}