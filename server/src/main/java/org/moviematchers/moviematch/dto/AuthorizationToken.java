package org.moviematchers.moviematch.dto;

import java.time.Instant;

public interface AuthorizationToken {
	String getValue();

	Instant getIssuedTime();

	Instant getExpirationTime();
}
