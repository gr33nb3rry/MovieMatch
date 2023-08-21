package org.moviematchers.moviematch.dto;

import java.time.Instant;

public interface AuthorizationToken {
	String getToken();

	Instant getIssuedTime();

	Instant getExpirationTime();
}
