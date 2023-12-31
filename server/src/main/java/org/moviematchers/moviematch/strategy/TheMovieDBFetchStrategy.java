package org.moviematchers.moviematch.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.configuration.TheMovieDBProviderCondition;
import org.moviematchers.moviematch.configuration.TheMovieDBProviderConfiguration;
import org.moviematchers.moviematch.dto.*;
import org.moviematchers.moviematch.type.MovieGenre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Conditional(TheMovieDBProviderCondition.class)
public class TheMovieDBFetchStrategy implements MovieFetchStrategy {
	private final static String PARSE_ERROR_MESSAGE = "failed to parse json data from themoviedb.org api";
	private final static List<Movie> EMPTY_LIST = Collections.emptyList();
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final static WebClient WEB_CLIENT = WebClient.builder()
		.baseUrl("https://api.themoviedb.org")
		.build();

	private final static Map<MovieGenre, String> MOVIE_GENRE_TOKEN_MAP = new EnumMap<>(MovieGenre.class) {{
		put(MovieGenre.ACTION, "28");
		put(MovieGenre.ADVENTURE, "12");
		put(MovieGenre.ANIMATION, "16");
		put(MovieGenre.COMEDY, "35");
		put(MovieGenre.CRIME, "80");
		put(MovieGenre.DOCUMENTARY, "99");
		put(MovieGenre.DRAMA, "18");
		put(MovieGenre.FAMILY, "10751");
		put(MovieGenre.FANTASY, "14");
		put(MovieGenre.HISTORY, "36");
		put(MovieGenre.HORROR, "27");
		put(MovieGenre.MUSIC, "10402");
		put(MovieGenre.MYSTERY, "9648");
		put(MovieGenre.ROMANCE, "10749");
		put(MovieGenre.SCIENCE_FICTION, "878");
		put(MovieGenre.TV, "10770");
		put(MovieGenre.THRILLER, "53");
		put(MovieGenre.WAR, "10752");
		put(MovieGenre.WESTERN, "37");
	}};
	private final static Map<String, MovieGenre> MOVIE_GENRE_TYPE_MAP = TheMovieDBFetchStrategy.MOVIE_GENRE_TOKEN_MAP.keySet().stream()
		.collect(Collectors.toMap(TheMovieDBFetchStrategy.MOVIE_GENRE_TOKEN_MAP::get, Function.identity()));

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
			logger.error(TheMovieDBFetchStrategy.PARSE_ERROR_MESSAGE);
			return TheMovieDBFetchStrategy.EMPTY_LIST;
		}
		String jsonResponse = entity.getBody();

		List<Movie> movies = new ArrayList<>();
		try {
			JsonNode matcherRootNode = TheMovieDBFetchStrategy.OBJECT_MAPPER.readTree(jsonResponse).get("results");
			for (JsonNode matcherSubNode : matcherRootNode) {
				String releaseDate = matcherSubNode.get("release_date").textValue();
				// if it's not released yet, skip it since we don't need it.
				if (releaseDate.isBlank()) continue;

				String posterPath = matcherSubNode.get("poster_path").textValue();
				URL posterURL = null;
				if (!posterPath.isBlank()) {
					posterURL = new URL("https://image.tmdb.org/t/p/original" + posterPath);
				}

				Set<MovieGenre> genreSet = EnumSet.noneOf(MovieGenre.class);
				JsonNode genreRootNode = matcherSubNode.get("genre_ids");
				for (JsonNode genreSubNode : genreRootNode) {
					if (!genreSubNode.isInt()) {
						logger.error(TheMovieDBFetchStrategy.PARSE_ERROR_MESSAGE);
						return TheMovieDBFetchStrategy.EMPTY_LIST;
					}
					String genreToken = genreSubNode.asText();
					MovieGenre genre = TheMovieDBFetchStrategy.MOVIE_GENRE_TYPE_MAP.get(genreToken);
					if (genre != null) {
						genreSet.add(genre);
					}
				}

				Movie movie = new MovieImpl(
					matcherSubNode.get("original_title").textValue(),
					matcherSubNode.get("overview").textValue(),
					LocalDate.parse(releaseDate),
					matcherSubNode.get("vote_average").doubleValue(),
					genreSet,
					matcherSubNode.get("adult").booleanValue(),
					posterURL
				);
				movies.add(movie);
			}
		} catch (Exception exception) {
			logger.error(TheMovieDBFetchStrategy.PARSE_ERROR_MESSAGE, exception);
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

	private String mapGenreQuery(Set<MovieGenre> genreTypes) {
		return genreTypes.stream()
			.map(TheMovieDBFetchStrategy.MOVIE_GENRE_TOKEN_MAP::get)
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
					builder.queryParam("with_genres", this.mapGenreQuery(genresSet));
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
