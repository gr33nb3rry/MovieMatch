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
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@Conditional(JWTAuthorizationCondition.class)
public class JWTAuthorizationSecurityConfiguration {
	private final JWTAuthorizationConfiguration configuration;

	public JWTAuthorizationSecurityConfiguration(JWTAuthorizationConfiguration configuration) {
		this.configuration = configuration;
	}

	@Bean
	@Order(2)
	public SecurityFilterChain jwtAuthorizationSecurityFilterChain(HttpSecurity security) throws Exception {
		security
			.authorizeHttpRequests(requestsConfigurer -> requestsConfigurer
				.requestMatchers("/quote", "/status").permitAll()
				.anyRequest().authenticated()
			)
			.sessionManagement(sessionConfigurer -> sessionConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(exceptionConfigurer ->
				exceptionConfigurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			)
			.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer
				.jwt(Customizer.withDefaults())
			)
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(Customizer.withDefaults());

		return security.build();
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
