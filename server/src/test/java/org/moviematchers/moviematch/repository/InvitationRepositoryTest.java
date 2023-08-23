package org.moviematchers.moviematch.repository;

import org.junit.jupiter.api.Test;
import org.moviematchers.moviematch.entity.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class InvitationRepositoryTest {
    @Autowired
    private InvitationRepository underTest;
    @Test
    void findByUserIDInvited() {
        Long invitedID = 3L;
        Invitation invite = new Invitation(4L, invitedID);
        underTest.save(invite);

        List<Invitation> result = underTest.findByUserIDInvited(invitedID);

        assertThat(result.get(0).getUserIDInvited()).isEqualTo(invitedID);
    }
}