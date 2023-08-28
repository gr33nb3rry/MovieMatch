package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentialsImpl implements UserCredentials {
	private final String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private final String password;

	public UserCredentialsImpl(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
}
