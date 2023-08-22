package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.Invitation;
import org.moviematchers.moviematch.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> addInvitation(@RequestBody Invitation invitation) {
        boolean result = invitationService.addInvitation(invitation);
        if (result) {
            return new ResponseEntity<>(invitation.getInvitationID(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(-1L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<String> deleteInvitation(@RequestParam Long id) {
        boolean result = invitationService.deleteInvitation(id);
        if (result) {
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete invitation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
