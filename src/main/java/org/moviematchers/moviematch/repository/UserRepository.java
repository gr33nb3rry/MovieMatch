package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.MovieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MovieUser, Long> {

    MovieUser findByUserName(String name);
}
