package org.moviematchers.moviematch.mapper;

import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.entity.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserEntityCredentialsMapper implements Mapper<UserCredentials, UserEntity> {
	@Override
	public UserEntity map(UserCredentials credentials) {
		return new UserEntity(credentials.getUsername(), credentials.getPassword());
	}
}
