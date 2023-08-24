package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.repository.InvitationRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {
    @Mock
    private InvitationRepository invitationRepository;
    private InvitationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new InvitationService(invitationRepository);
    }
    @Test
    void canGetAllInvitations() {
        // given
        List<Invitation> invitations = new ArrayList<>();
        invitations.add(new Invitation(2L, 1L));
        invitations.add(new Invitation(3L, 2L));
        invitations.add(new Invitation(4L, 3L));
        when(invitationRepository.findAll()).thenReturn(invitations);
        // when
        List<Invitation> result = underTest.getAllInvitations();
        // then
        verify(invitationRepository).findAll();
        assertThat(result).isEqualTo(invitations);
    }

    @Test
    void canAddInvitation() {
        // given
        Long user1Id = 1L;
        Long user2Id = 2L;
        Invitation invitation = new Invitation(user1Id, user2Id);
        // when
        boolean result = underTest.addInvitation(invitation);
        // then
        ArgumentCaptor<Invitation> argumentCaptor = ArgumentCaptor.forClass(Invitation.class);
        verify(invitationRepository).save(argumentCaptor.capture());

        Invitation captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(invitation);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void canDeleteInvitation() {
        // given
        Long userId = 1L;

        // when
        boolean result = underTest.deleteInvitation(userId);
        // then
        verify(invitationRepository).deleteById(userId);
        assertThat(result).isEqualTo(true);
    }
    @Test
    void cannotDeleteInvitation() {
        // given
        Long userId = 1L;
        doThrow(new RuntimeException("Some error")).when(invitationRepository).deleteById(userId);

        // when
        boolean result = underTest.deleteInvitation(userId);

        // then
        verify(invitationRepository).deleteById(userId);
        assertThat(result).isEqualTo(false);
    }

    @Test
    void canGetAllInvitationsByID() {
        // given
        Long userId = 1L;
        List<Invitation> invitations = new ArrayList<>();
        invitations.add(new Invitation(2L, userId));
        invitations.add(new Invitation(3L, userId));
        invitations.add(new Invitation(4L, userId));
        when(invitationRepository.findByUserIDInvited(userId)).thenReturn(invitations);
        // when
        List<Invitation> result = underTest.getAllInvitationsByID(userId);
        // then
        verify(invitationRepository).findByUserIDInvited(userId);
        assertThat(result).isEqualTo(invitations);
    }

    @Test
    void canGetLastInvitationsByID() {
        // given
        Long userId = 2L;
        Invitation invitation1 = new Invitation(1L, userId);
        Invitation invitation2 = new Invitation(3L, userId);
        List<Invitation> invitations = new ArrayList<>();
        invitations.add(invitation1);
        invitations.add(invitation2);
        when(invitationRepository.findByUserIDInvited(userId)).thenReturn(invitations);
        // when
        Invitation result = underTest.getLastInvitationsByID(userId);
        // then
        verify(invitationRepository).findByUserIDInvited(userId);

        assertThat(result.getInvitationID()).isEqualTo(invitation2.getInvitationID());
    }
}