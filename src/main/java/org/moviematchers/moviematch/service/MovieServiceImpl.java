package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.dto.Movie;
import org.moviematchers.moviematch.dto.MovieFilterBuilder;
import org.moviematchers.moviematch.strategy.MovieFetchStrategy;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class MovieServiceImpl implements MovieService {
	private final MovieFetchStrategy[] strategies;

	public MovieServiceImpl(MovieFetchStrategy... strategies) {
		this.strategies = strategies;
	}

	@Override
	public List<Movie> fetch(String matcher) {
		List<Movie> results = new ArrayList<>();
		for (MovieFetchStrategy strategy : this.strategies) {
			List<Movie> fetched = strategy.fetch(matcher);
			results.addAll(fetched);
		}
		return results;
	}

	@Override
	public List<Movie> fetch(Consumer<MovieFilterBuilder> filter) {
		List<Movie> results = new ArrayList<>();
		for (MovieFetchStrategy strategy : this.strategies) {
			List<Movie> fetched = strategy.fetch(filter);
			results.addAll(fetched);
		}
		return results;
	}
}
