package org.moviematchers.moviematch.strategy;

import org.moviematchers.moviematch.dto.Movie;

import java.util.List;

public interface MovieFetchStrategy {
	List<Movie> fetch(String match);
}
