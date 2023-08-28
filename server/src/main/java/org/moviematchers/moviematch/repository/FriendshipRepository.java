package org.moviematchers.moviematch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.moviematchers.moviematch.entity.MovieFriendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<MovieFriendship, Long> {
    List<MovieFriendship> findByFirstUserEntityId(long id);

    List<MovieFriendship> findBySecondUserEntityId(Long id);
}
