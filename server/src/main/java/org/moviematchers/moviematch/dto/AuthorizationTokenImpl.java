package org.moviematchers.moviematch.dto;

import java.time.Instant;

public class AuthorizationTokenImpl implements AuthorizationToken {
	private final String token;
	private final Instant issuedTime;
	private final Instant expirationTime;

	public AuthorizationTokenImpl(String token, Instant issuedTime, Instant expirationTime) {
		this.token = token;
		this.issuedTime = issuedTime;
		this.expirationTime = expirationTime;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public Instant getIssuedTime() {
		return this.issuedTime;
	}

	@Override
	public Instant getExpirationTime() {
		return this.expirationTime;
	}
}
