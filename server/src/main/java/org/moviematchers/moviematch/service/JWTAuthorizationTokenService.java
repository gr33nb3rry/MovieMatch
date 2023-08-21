package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.configuration.JWTAuthorizationCondition;
import org.moviematchers.moviematch.dto.AuthorizationToken;
import org.moviematchers.moviematch.dto.AuthorizationTokenImpl;

import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@Conditional(JWTAuthorizationCondition.class)
public class JWTAuthorizationTokenService implements AuthorizationTokenService {
	private final JwtEncoder encoder;

	public JWTAuthorizationTokenService(JwtEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public AuthorizationToken generateToken(Authentication authentication, long validTimeAmount, ChronoUnit unit) {
		Instant issuedTime = Instant.now();
		Instant expirationTime = issuedTime.plus(validTimeAmount, unit);
		String scope = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
			.issuer("self")
			.issuedAt(issuedTime)
			.expiresAt(expirationTime)
			.subject(authentication.getName())
			.claim("scope", scope)
			.build();

		String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		return new AuthorizationTokenImpl(token, issuedTime, expirationTime);
	}
}
