package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
	List<Invitation> findByUserIDInvited(Long id);
}
