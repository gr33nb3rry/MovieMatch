package org.moviematchers.moviematch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.repository.FriendshipRepository;
import org.moviematchers.moviematch.entity.MovieUser;

import java.util.List;
import java.util.Objects;

@Service
public class FriendshipService {
    private final Logger logger = LoggerFactory.getLogger(FriendshipService.class);
    private final FriendshipRepository friendshipRepository;
    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }
    //get friendships for a specific user
    public List<MovieFriendship> getFriendshipsForUser(Long id) {
        List<MovieFriendship> findByUser1ID = friendshipRepository.findByUser1IDUserID(id);
        List<MovieFriendship> findByUser2ID = friendshipRepository.findByUser2IDUserID(id);
        findByUser1ID.addAll(findByUser2ID);
        return findByUser1ID;
    }


    public List<MovieFriendship> getAllFriendships() {
        return friendshipRepository.findAll();
    }

    public boolean addFriendship(MovieFriendship friendship) {
        try {
            Long user1ID = friendship.getUser1ID().getUserID();
            Long user2ID = friendship.getUser2ID().getUserID();
            logger.info("New friendship user1_id: {}", user1ID);
            logger.info("New friendship user2_id: {}", user2ID);
            if (!Objects.equals(user1ID, user2ID)) {
                logger.info("user1_id and user2_id aren't equal. SAVING");
                friendshipRepository.save(friendship);
                return true;
            } else {
                logger.error("user1_id and user2_id are the same");
                return false;
            }
        }
        catch(Exception e) {
            return false;
        }
    }
}
