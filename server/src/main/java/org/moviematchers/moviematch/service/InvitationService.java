package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;
    @Autowired
    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    public void addInvitation(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }

    public List<Invitation> getAllInvitationsByID(Long id) {
        return invitationRepository.findByUserIDInvited(id);
    }

    public Invitation getLastInvitationsByID(Long id) {
        List<Invitation> invitations = invitationRepository.findByUserIDInvited(id);
        if (invitations.size() > 0)
            return invitations.get(invitations.size()-1);
        else return null;
    }
}
