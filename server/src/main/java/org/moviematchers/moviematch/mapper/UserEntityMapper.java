package org.moviematchers.moviematch.mapper;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper implements Mapper<User, UserEntity> {
	private final Mapper<UserCredentials, UserEntity> mapper;

	public UserEntityMapper(Mapper<UserCredentials, UserEntity> mapper) {
		this.mapper = mapper;
	}

	@Override
	public UserEntity map(User user) {
		UserCredentials credentials = user.getCredentials();
		if (credentials == null) {
			return new UserEntity(user.getId(), null, null);
		}
		UserEntity entity = this.mapper.map(credentials);
		entity.setId(user.getId());
		return entity;
	}
}
