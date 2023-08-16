package org.moviematchers.moviematch.dto;

import java.time.LocalDate;

public class MovieImpl implements Movie {
	private final String title;
	private final String description;
	private final LocalDate releaseDate;
	private final Double rating;
	private final Boolean adultRated;

	public MovieImpl(String title, String description, LocalDate releaseDate, Double rating, Boolean adultRated) {
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.rating = rating;
		this.adultRated = adultRated;
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
		return this.releaseDate;
	}

	@Override
	public Double getRating() {
		return this.rating;
	}

	public Boolean isAdultRated() {
		return this.adultRated;
	}
}
