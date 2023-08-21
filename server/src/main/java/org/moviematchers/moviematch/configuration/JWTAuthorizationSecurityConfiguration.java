package org.moviematchers.moviematch.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Conditional(JWTAuthorizationCondition.class)
public class JWTAuthorizationSecurityConfiguration {
	private final JWTAuthorizationConfiguration configuration;


	public JWTAuthorizationSecurityConfiguration(JWTAuthorizationConfiguration configuration) {
		this.configuration = configuration;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain tokenAuthorizationSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer
				.jwt(jwtConfigurer -> jwtConfigurer
					.decoder(this.jwtDecoder())
				)
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.configuration.getPublicKey()).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(this.configuration.getPublicKey())
			.privateKey(this.configuration.getPrivateKey())
			.build();
		JWKSource<SecurityContext> source = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(source);
	}
}
