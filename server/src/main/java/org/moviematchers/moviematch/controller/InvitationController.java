package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "invite")
public class InvitationController {
    private final InvitationService invitationService;
    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("all")
    public List<Invitation> getAllInvitations() {return invitationService.getAllInvitations();}
    @GetMapping("allByID")
    public List<Invitation> getAllInvitationsByID(@RequestParam Long id) {
        return invitationService.getAllInvitationsByID(id);
    }
    @GetMapping("byID")
    public Invitation getLastInvitationsByID(@RequestParam Long id) {
        return invitationService.getLastInvitationsByID(id);
    }

    @PostMapping
    public void addInvitation(@RequestBody Invitation invitation) {
        invitationService.addInvitation(invitation);
    }
    @DeleteMapping
    public void deleteInvitation(@RequestParam Long id) {
        invitationService.deleteInvitation(id);
    }
}
