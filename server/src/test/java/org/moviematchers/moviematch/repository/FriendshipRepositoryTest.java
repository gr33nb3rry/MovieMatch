package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FriendshipRepositoryTest {
    @Autowired
    private FriendshipRepository underTest;
    @Autowired
    private UserRepository userUnderTest;

    @Test
    void shouldReturnListOfFriendshipsByUser1ID() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        userUnderTest.save(user1);
        userUnderTest.save(user2);

        MovieFriendship friendship = new MovieFriendship(user1, user2);
        underTest.save(friendship);

        List<MovieFriendship> result = underTest.findByFirstUserEntityId(user1.getId());

        assertThat(result.get(0).getFirstUserEntity().getId()).isEqualTo(user1.getId());
    }

    @Test
    void shouldReturnListOfFriendshipsByUser2ID() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        userUnderTest.save(user1);
        userUnderTest.save(user2);

        MovieFriendship friendship = new MovieFriendship(user1, user2);
        underTest.save(friendship);

        List<MovieFriendship> result = underTest.findBySecondUserEntityId(user2.getId());

        assertThat(result.get(0).getSecondUserEntity().getId()).isEqualTo(user2.getId());
    }
}