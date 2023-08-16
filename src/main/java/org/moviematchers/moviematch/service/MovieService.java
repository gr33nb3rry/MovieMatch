package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieFetchOptions;
import org.moviematchers.moviematch.dto.MovieFilter;

import java.util.List;
import java.util.function.Consumer;

public interface MovieService {
	List<Movie> fetch(Consumer<MovieFetchOptions> options, String matcher);

	List<Movie> fetch(Consumer<MovieFetchOptions> options, Consumer<MovieFilter> filter);
}
