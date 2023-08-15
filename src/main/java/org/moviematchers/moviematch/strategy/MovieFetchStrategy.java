package org.moviematchers.moviematch.strategy;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieFilterBuilder;

import java.util.List;
import java.util.function.Consumer;

public interface MovieFetchStrategy {
	List<Movie> fetch(String matcher);

	List<Movie> fetch(Consumer<MovieFilterBuilder> filter);
}
