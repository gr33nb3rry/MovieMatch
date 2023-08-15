package org.moviematchers.moviematch.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.moviematchers.moviematch.configuration.TheMovieDBProviderConfiguration;
import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ConditionalOnBean(TheMovieDBProviderConfiguration.class)
public class TheMovieDBFetchStrategy implements MovieFetchStrategy {
	private final static WebClient WEB_CLIENT = WebClient.builder()
		.baseUrl("https://api.themoviedb.org")
		.build();

	private final TheMovieDBProviderConfiguration configuration;
	private final Logger logger = LoggerFactory.getLogger(TheMovieDBFetchStrategy.class);

	public TheMovieDBFetchStrategy(TheMovieDBProviderConfiguration configuration) {
		logger.info(configuration.getAPIKey());
		this.configuration = configuration;
	}

	@Override
	public List<Movie> fetch(String match) {
		WebClient.ResponseSpec spec = TheMovieDBFetchStrategy.WEB_CLIENT.method(HttpMethod.GET).uri(builder -> builder
			.path("/3/search/movie")
			.queryParam("query", match)
			.queryParam("api_key", this.configuration.getAPIKey())
			.build()
		).accept(MediaType.APPLICATION_JSON).retrieve();

		ResponseEntity<String> entity = spec.toEntity(String.class).block();
		List<Movie> movies = new ArrayList<>();
		if (entity == null || !entity.getStatusCode().is2xxSuccessful()) {
			return null;
		}
		String body = entity.getBody();

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode results = mapper.readTree(body).get("results");
			for (JsonNode matchers : results) {
				String releaseDate = matchers.get("release_date").asText();
				// if it's not released yet, skip it since we don't need it.
				if (releaseDate.isBlank()) continue;

				Movie movie = new MovieImpl(
					matchers.get("original_title").asText(),
					matchers.get("overview").asText(),
					LocalDate.parse(releaseDate),
					matchers.get("vote_average").asDouble(),
					matchers.get("adult").asBoolean()
				);
				movies.add(movie);
			}
		} catch (Exception exception) {
			logger.error("failed to parse json data from themoviedb.org api", exception);
			return null;
		}
		return movies;
	}
}
