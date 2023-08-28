package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserCredentialsImpl.class)
public interface UserCredentials {
	String getUsername();

	String getPassword();
}
