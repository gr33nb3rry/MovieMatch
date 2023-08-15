package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.strategy.MovieFetchStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;


@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieMatchApplication {
	private final MovieFetchStrategy strategy;

	private final Logger logger = LoggerFactory.getLogger(MovieMatchApplication.class);

	public MovieMatchApplication(MovieFetchStrategy strategy) {
		this.strategy = strategy;
		List<Movie> movies = this.strategy.fetch("moterys meluoja geriau");
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
