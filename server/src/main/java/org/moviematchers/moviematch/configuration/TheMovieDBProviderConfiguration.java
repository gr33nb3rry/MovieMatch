package org.moviematchers.moviematch.configuration;

import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Conditional;
import org.springframework.validation.annotation.Validated;

@Validated
@Conditional(TheMovieDBProviderCondition.class)
@ConfigurationProperties("movie-match.provider.the-movie-db")
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
}
