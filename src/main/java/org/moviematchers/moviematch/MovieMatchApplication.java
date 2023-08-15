package org.moviematchers.moviematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieMatchApplication.class, args);

	}

}
