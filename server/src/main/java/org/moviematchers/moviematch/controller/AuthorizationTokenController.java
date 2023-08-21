package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.configuration.JWTAuthorizationCondition;
import org.moviematchers.moviematch.dto.AuthorizationToken;
import org.moviematchers.moviematch.service.AuthorizationTokenService;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/authorization/token")
@Conditional(JWTAuthorizationCondition.class)
public class AuthorizationTokenController {
	private final AuthorizationTokenService service;

	public AuthorizationTokenController(AuthorizationTokenService service) {
		this.service = service;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthorizationToken token(Authentication authentication) {
		return this.service.generateToken(authentication, 1, ChronoUnit.HALF_DAYS);
	}
}
