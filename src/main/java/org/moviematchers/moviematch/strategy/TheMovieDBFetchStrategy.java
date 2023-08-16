package org.moviematchers.moviematch.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.configuration.TheMovieDBProviderConfiguration;
import org.moviematchers.moviematch.dto.*;
import org.moviematchers.moviematch.type.MovieGenre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ConditionalOnBean(TheMovieDBProviderConfiguration.class)
public class TheMovieDBFetchStrategy implements MovieFetchStrategy {
	private final static List<Movie> EMPTY_LIST = Collections.emptyList();
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final static String[] MOVIE_GENRE_TOKENS = new String[] {
		"28", "12", "16", "35", "80", "99", "18", "10751", "14", "36", "27",
		"10402", "9648", "10749", "878", "10770", "53", "10752", "37"
	};
	private final static WebClient WEB_CLIENT = WebClient.builder()
		.baseUrl("https://api.themoviedb.org")
		.build();

	private final TheMovieDBProviderConfiguration configuration;
	private final Logger logger = LoggerFactory.getLogger(TheMovieDBFetchStrategy.class);

	public TheMovieDBFetchStrategy(TheMovieDBProviderConfiguration configuration) {
		this.configuration = configuration;
	}

	private void mapFetchOptions(UriBuilder builder, Consumer<MovieFetchOptions> consumer) {
		MovieFetchOptions options = new MovieFetchOptionsImpl();
		consumer.accept(options);
		if (options.getPage() != null) {
			builder.queryParam("page", options.getPage());
		}
	}

	private List<Movie> deserializeResponse(WebClient.ResponseSpec spec) {
		ResponseEntity<String> entity = spec.toEntity(String.class).block();
		if (entity == null || !entity.getStatusCode().is2xxSuccessful()) {
			logger.error("failed to parse json data from themoviedb.org api");
			return TheMovieDBFetchStrategy.EMPTY_LIST;
		}
		String jsonResponse = entity.getBody();

		List<Movie> movies = new ArrayList<>();
		try {
			JsonNode results = TheMovieDBFetchStrategy.OBJECT_MAPPER.readTree(jsonResponse).get("results");
			for (JsonNode matchers : results) {
				String releaseDate = matchers.get("release_date").asText();
				// if it's not released yet, skip it since we don't need it.
				if (releaseDate.isBlank()) continue;

				String posterPath = matchers.get("poster_path").asText();
				URL posterURL = null;
				if (!posterPath.isBlank()) {
					posterURL = new URL("https://image.tmdb.org/t/p/original" + posterPath);
				}

				Movie movie = new MovieImpl(
					matchers.get("original_title").asText(),
					matchers.get("overview").asText(),
					LocalDate.parse(releaseDate),
					matchers.get("vote_average").asDouble(),
					matchers.get("adult").asBoolean(),
					posterURL
				);
				movies.add(movie);
			}
		} catch (Exception exception) {
			logger.error("failed to parse json data from themoviedb.org api", exception);
			return TheMovieDBFetchStrategy.EMPTY_LIST;
		}
		return movies;
	}

	@Override
	public List<Movie> fetch(Consumer<MovieFetchOptions> optionsConsumer, String matcher) {
		WebClient.ResponseSpec spec = TheMovieDBFetchStrategy.WEB_CLIENT.method(HttpMethod.GET).uri(builder -> {
			builder.path("/3/search/movie")
				.queryParam("query", matcher)
				.queryParam("api_key", this.configuration.getAPIKey());
			this.mapFetchOptions(builder, optionsConsumer);
			return builder.build();
		}).accept(MediaType.APPLICATION_JSON).retrieve();
		return this.deserializeResponse(spec);
	}

	private String mapGenres(Set<MovieGenre> genreTypes) {
		return genreTypes.stream()
			.map(genreType -> TheMovieDBFetchStrategy.MOVIE_GENRE_TOKENS[genreType.ordinal()])
			.collect(Collectors.joining(","));
	}

	@Override
	public List<Movie> fetch(Consumer<MovieFetchOptions> optionsConsumer, Consumer<MovieFilter> filterConsumer) {
		WebClient.ResponseSpec spec = TheMovieDBFetchStrategy.WEB_CLIENT.method(HttpMethod.GET)
			.uri(builder -> {
				builder.path("/3/discover/movie");
				MovieFilter filter = new MovieFilterImpl();
				filterConsumer.accept(filter);
				Boolean adultRated = filter.isAdultRated();
				if (adultRated != null) {
					builder.queryParam("include_adult", adultRated);
				}
				MovieGenre[] genres = filter.getGenres();
				if (genres != null && genres.length != 0) {
					// TODO: MAYBE WE DONT NEED SET IN HERE?
					Set<MovieGenre> genresSet = Set.of(genres);
					builder.queryParam("with_genres", this.mapGenres(genresSet));
				}
				CountryCode originCountry = filter.getOriginCountry();
				if (originCountry != null) {
					builder.queryParam("with_origin_country", originCountry.getAlpha2());
				}
				LocalDate releaseDateStart = filter.getReleaseDateStart();
				if (releaseDateStart != null) {
					builder.queryParam("primary_release_date.gte", releaseDateStart.toString());
				}
				LocalDate releaseDateEnd = filter.getReleaseDateEnd();
				if (releaseDateEnd != null) {
					builder.queryParam("primary_release_date.lte", releaseDateEnd.toString());
				}

				MovieFetchOptions options = new MovieFetchOptionsImpl();
				optionsConsumer.accept(options);
				if (options.getPage() != null) {
					builder.queryParam("page", options.getPage());
				}
				return builder.build();
			})
			.headers(httpHeaders ->
				httpHeaders.setBearerAuth(this.configuration.getAPIToken())
			)
			.accept(MediaType.APPLICATION_JSON)
			.acceptCharset(StandardCharsets.UTF_8)
			.retrieve();
		return this.deserializeResponse(spec);
	}
}
