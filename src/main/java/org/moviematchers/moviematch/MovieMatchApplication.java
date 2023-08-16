package org.moviematchers.moviematch;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.service.MovieService;
import org.moviematchers.moviematch.type.MovieGenre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieMatchApplication {
	private final Logger logger = LoggerFactory.getLogger(MovieMatchApplication.class);
	private final MovieService service;

	public MovieMatchApplication(MovieService service) {
		this.service = service;
		// Just a test of fetching filtered data.
		List<Movie> movies = this.service.fetch(options -> options.setPage(2), filter -> filter
			.setGenres(MovieGenre.TV)
			.setAdultRated(true)
			.setOriginCountry(CountryCode.LV)
		);
		for (Movie movie : movies) {
			logger.info("Movie title: {}", movie.getTitle());
			logger.info("Movie description: {}", movie.getDescription());
			logger.info("Movie release date: {}", movie.getReleaseDate());
			logger.info("Movie rating: {}", movie.getRating());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieMatchApplication.class, args);
	}

}
