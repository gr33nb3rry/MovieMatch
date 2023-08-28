package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User {
	Long getId();

	UserCredentials getCredentials();
}
