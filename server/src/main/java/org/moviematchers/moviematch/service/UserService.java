package org.moviematchers.moviematch.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.entity.UserEntity;
import org.moviematchers.moviematch.mapper.Mapper;
import org.moviematchers.moviematch.repository.UserRepository;
import org.moviematchers.moviematch.type.Presence;
import org.moviematchers.moviematch.validation.ValidUserId;
import org.moviematchers.moviematch.validation.ValidUserPassword;
import org.moviematchers.moviematch.validation.ValidUsername;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final Mapper<UserEntity, User> mapper;
    private final Mapper<UserCredentials, UserEntity> entityMapper;

    public UserService(UserRepository repository, PasswordEncoder encoder, Mapper<UserEntity, User> userMapper, Mapper<UserCredentials, UserEntity> entityMapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = userMapper;
        this.entityMapper = entityMapper;
    }

    public Long getUserId(@Valid @ValidUsername String username) {
        UserEntity entity = repository.findByUsernameIgnoreCase(username);
        if (entity == null) {
            return null;
        }
        return entity.getId();
    }

    public void registerUser(@Valid @ValidUsername(presence = Presence.ABSENT) @ValidUserPassword UserCredentials credentials) {
        String password = credentials.getPassword();
        String encodedPassword = encoder.encode(password);

        UserEntity entity = this.entityMapper.map(credentials);
        entity.setPassword(encodedPassword);

        repository.save(entity);
    }

    @Transactional
    public void changeUserPassword(@Valid @ValidUserId(presence = Presence.PRESENT) @ValidUserPassword User user) {
        UserEntity entity = repository.getReferenceById(user.getId());
        UserCredentials credentials = user.getCredentials();
        String password = credentials.getPassword();
        String encodedPassword = encoder.encode(password);

        entity.setPassword(encodedPassword);
    }
}
