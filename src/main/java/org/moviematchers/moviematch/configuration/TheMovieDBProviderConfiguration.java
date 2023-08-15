package org.moviematchers.moviematch.configuration;

import jakarta.validation.constraints.NotBlank;
import org.moviematchers.moviematch.strategy.TheMovieDBFetchStrategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("movie-matcher.provider.the-movie-db")
@ConditionalOnProperty("movie-matcher.provider.the-movie-db.api-key")
public class TheMovieDBProviderConfiguration {
	@NotBlank(message = "api key cannot be blank or null")
	private final String apiKey;

	@ConstructorBinding
	public TheMovieDBProviderConfiguration(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAPIKey() {
		return this.apiKey;
	}

	@Bean
	public TheMovieDBFetchStrategy theMovieDBFetchStrategy() {
		return new TheMovieDBFetchStrategy(this);
	}
}
