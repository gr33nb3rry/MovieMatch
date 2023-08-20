package org.moviematchers.moviematch.dto;

import org.moviematchers.moviematch.type.MovieGenre;

import java.net.URL;
import java.time.LocalDate;
import java.util.Set;

public class MovieImpl implements Movie {
	private final String title;
	private final String description;
	private final LocalDate date;
	private final Double rating;
	private final Set<MovieGenre> genres;
	private final Boolean adult;
	private final URL poster;

	public MovieImpl(String title, String description, LocalDate date, Double rating, Set<MovieGenre> genres, Boolean adult, URL poster) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.rating = rating;
		this.genres = genres;
		this.adult = adult;
		this.poster = poster;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public LocalDate getReleaseDate() {
		return this.date;
	}

	@Override
	public Double getRating() {
		return this.rating;
	}

	@Override
	public Set<MovieGenre> getGenres() {
		return this.genres;
	}

	public Boolean isAdultRated() {
		return this.adult;
	}

	@Override
	public URL getPosterURL() {
		return this.poster;
	}
}
