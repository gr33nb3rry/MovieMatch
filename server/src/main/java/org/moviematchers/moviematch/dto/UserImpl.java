package org.moviematchers.moviematch.dto;

public class UserImpl implements User {
	private final Long id;

	private final UserCredentials credentials;

	public UserImpl(Long id, UserCredentials credentials) {
		this.id = id;
		this.credentials = credentials;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public UserCredentials getCredentials() {
		return this.credentials;
	}
}