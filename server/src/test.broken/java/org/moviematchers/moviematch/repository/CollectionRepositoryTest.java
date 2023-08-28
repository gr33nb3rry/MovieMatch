package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CollectionRepositoryTest {
    @Autowired
    private CollectionRepository underTest;
    @Autowired
    private UserRepository userUnderTest;

    @Test
    void shouldReturnCollectionByUserID() {
        Long userID = 1L;
        UserEntity user = new UserEntity();
        userUnderTest.save(user);
        UserMovieCollection movie = new UserMovieCollection(user, "Batman", 7.5);
        underTest.save(movie);

        List<UserMovieCollection> result = underTest.findByUserEntityId(userID);

        assertThat(result.get(0).getUserEntity().getId()).isEqualTo(userID);
    }
}