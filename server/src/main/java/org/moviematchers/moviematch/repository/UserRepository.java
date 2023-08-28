package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameIgnoreCase(String username);

    boolean existsByIdOrUsername(Long id, String username);

    boolean existsByUsernameIgnoreCase(String username);
}
