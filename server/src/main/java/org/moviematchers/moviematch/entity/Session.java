package org.moviematchers.moviematch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_match_session")
public class Session {
    @Id
    @SequenceGenerator(
            name = "session_sequence",
            sequenceName = "session_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "session_sequence"
    )
    @Column(name = "session_id")
    private Long sessionID;
    @Column(name = "invitation_id")
    private Long invitationID;
    @Column(name = "user_1_id")
    private Long user1ID;
    @Column(name = "user_2_id")
    private Long user2ID;


    public Session() {
    }

    public Session(Long sessionID, Long invitationID, Long user1ID, Long user2ID) {
        this.sessionID = sessionID;
        this.invitationID = invitationID;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
    }

    public Long getSessionID() {
        return sessionID;
    }

    public void setSessionID(Long sessionID) {
        this.sessionID = sessionID;
    }

    public Long getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(Long invitationID) {
        this.invitationID = invitationID;
    }

    public Long getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(Long user1ID) {
        this.user1ID = user1ID;
    }

    public Long getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(Long user2ID) {
        this.user2ID = user2ID;
    }

}
