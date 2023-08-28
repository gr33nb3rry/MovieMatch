package org.moviematchers.moviematch.mapper;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.dto.UserImpl;
import org.moviematchers.moviematch.entity.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, User> {
	private final Mapper<UserEntity, UserCredentials> mapper;

	public UserMapper(Mapper<UserEntity, UserCredentials> mapper) {
		this.mapper = mapper;
	}

	@Override
	public User map(UserEntity entity) {
		return new UserImpl(entity.getId(), this.mapper.map(entity));
	}
}
