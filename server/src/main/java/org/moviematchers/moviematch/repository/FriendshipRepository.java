package org.moviematchers.moviematch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.entity.MovieUser;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<MovieFriendship, Long> {
    List<MovieFriendship> findByUser1IDUserID(Long userID);
    List<MovieFriendship> findByUser2IDUserID(Long userID);
}
