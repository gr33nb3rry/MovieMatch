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
@ConditionalOnProperty({
	"movie-matcher.provider.the-movie-db.api-key",
	"movie-matcher.provider.the-movie-db.api-token"
})
public class TheMovieDBProviderConfiguration {
	@NotBlank(message = "api key cannot be blank or null")
	private final String apiKey;

	@NotBlank(message = "api token cannot be blank or null")
	private final String apiToken;

	@ConstructorBinding
	public TheMovieDBProviderConfiguration(String apiKey, String apiToken) {
		this.apiKey = apiKey;
		this.apiToken = apiToken;
	}

	public String getAPIKey() {
		return this.apiKey;
	}

	public String getAPIToken() {
		return this.apiToken;
	}

	@Bean
	public TheMovieDBFetchStrategy theMovieDBFetchStrategy() {
		return new TheMovieDBFetchStrategy(this);
	}
}
