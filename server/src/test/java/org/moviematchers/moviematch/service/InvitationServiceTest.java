package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.repository.FriendshipRepository;
import org.moviematchers.moviematch.repository.InvitationRepository;

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllInvitations() {
    }

    @Test
    void addInvitation() {
    }

    @Test
    void deleteInvitation() {
    }

    @Test
    void getAllInvitationsByID() {
    }

    @Test
    void getLastInvitationsByID() {
    }
}