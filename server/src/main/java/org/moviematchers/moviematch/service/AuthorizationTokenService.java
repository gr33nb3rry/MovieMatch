package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.dto.AuthorizationToken;
import org.springframework.security.core.Authentication;

import java.time.temporal.ChronoUnit;

public interface AuthorizationTokenService {
	AuthorizationToken generateToken(Authentication authentication, long validTimeAmount, ChronoUnit unit);
}