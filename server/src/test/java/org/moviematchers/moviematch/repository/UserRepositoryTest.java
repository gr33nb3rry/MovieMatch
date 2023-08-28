package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.UserEntity;
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
        UserEntity user = new UserEntity(1L,
                username,
                "password");
        underTest.save(user);
        // when
        UserEntity result = underTest.findByUsernameIgnoreCase(username);
        // then
        assertThat(result.getUsername()).isEqualTo(username);
    }

}