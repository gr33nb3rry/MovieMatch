package org.moviematchers.moviematch.dto;

import java.time.LocalDate;

public class MovieImpl implements Movie {
	private final String title;
	private final String description;
	private final LocalDate releaseDate;
	private final double rating;
	private final boolean adultRated;

	public MovieImpl(String title, String description, LocalDate releaseDate, double rating, boolean adultRated) {
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
	public double getRating() {
		return this.rating;
	}

	public boolean isAdultRated() {
		return this.adultRated;
	}
}
