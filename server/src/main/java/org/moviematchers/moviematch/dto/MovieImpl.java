package org.moviematchers.moviematch.dto;

import java.net.URL;
import java.time.LocalDate;

public class MovieImpl implements Movie {
	private final String title;
	private final String description;
	private final LocalDate date;
	private final Double rating;
	private final Boolean adult;
	private final URL poster;

	public MovieImpl(String title, String description, LocalDate releaseDate, Double rating, Boolean adultRated, URL posterURL) {
		this.title = title;
		this.description = description;
		this.date = releaseDate;
		this.rating = rating;
		this.adult = adultRated;
		this.poster = posterURL;
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

	public Boolean isAdultRated() {
		return this.adult;
	}

	@Override
	public URL getPosterURL() {
		return this.poster;
	}
}
