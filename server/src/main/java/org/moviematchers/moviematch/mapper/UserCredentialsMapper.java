package org.moviematchers.moviematch.mapper;

import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.dto.UserCredentialsImpl;
import org.moviematchers.moviematch.entity.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserCredentialsMapper implements Mapper<UserEntity, UserCredentials> {
	@Override
	public UserCredentials map(UserEntity entity) {
		return new UserCredentialsImpl(entity.getUsername(), entity.getPassword());
	}	
}