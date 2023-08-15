package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.MovieMatchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MovieMatchUser, Long> {
}
