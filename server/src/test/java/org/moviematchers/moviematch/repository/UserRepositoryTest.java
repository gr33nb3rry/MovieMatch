package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.MovieUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void itShouldReturnMovieUserByUsername() {
        // given
        String username = "testName";
        MovieUser user = new MovieUser(1L,
                username,
                "password",
                new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        underTest.save(user);
        // when
        MovieUser result = underTest.findByUserName(username);
        // then
        assertThat(result.getUserName()).isEqualTo(username);
    }

}