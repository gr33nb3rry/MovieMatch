package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.entity.UserMovieCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<UserMovieCollection, Long> {
}
