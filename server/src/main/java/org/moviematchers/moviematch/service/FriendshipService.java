package org.moviematchers.moviematch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.moviematchers.moviematch.entity.MovieFriendship;
import org.moviematchers.moviematch.repository.FriendshipRepository;

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
        List<MovieFriendship> findByUser1ID = friendshipRepository.findByFirstUserEntityId(id);
        List<MovieFriendship> findByUser2ID = friendshipRepository.findBySecondUserEntityId(id);
        findByUser1ID.addAll(findByUser2ID);
        return findByUser1ID;
    }

    public boolean addFriendship(MovieFriendship friendship) {
        try {
            Long user1ID = friendship.getFirstUserEntity().getId();
            Long user2ID = friendship.getSecondUserEntity().getId();
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
