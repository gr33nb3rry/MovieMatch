package org.moviematchers.moviematch;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.service.MovieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieMatchApplication.class, args);
	}

}