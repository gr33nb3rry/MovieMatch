package org.moviematchers.moviematch.configuration;

import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Conditional;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Validated
@ConfigurationProperties("movie-match.authorization.jwt")
@Conditional(JWTAuthorizationCondition.class)
public class JWTAuthorizationConfiguration {
	@NotNull(message = "rsa public key cannot be null")
	private final RSAPublicKey publicKey;

	@NotNull(message = "rsa private key cannot be null")
	private final RSAPrivateKey privateKey;

	@ConstructorBinding
	public JWTAuthorizationConfiguration(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public RSAPublicKey getPublicKey() {
		return this.publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return this.privateKey;
	}
}
