package org.moviematchers.moviematch.dto;

import java.time.Instant;

public class AuthorizationTokenImpl implements AuthorizationToken {
	private final String value;
	private final Instant issuedTime;
	private final Instant expirationTime;

	public AuthorizationTokenImpl(String value, Instant issuedTime, Instant expirationTime) {
		this.value = value;
		this.issuedTime = issuedTime;
		this.expirationTime = expirationTime;
	}

	@Override
	public String getValue() {
		return this.value;
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
