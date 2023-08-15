package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;

import java.util.Set;

public class MovieFilterImpl implements MovieFilter {
	private final Set<MovieGenre> genres;
	private final LocalDate releaseDateStart;
	private final LocalDate releaseDateEnd;
	private final CountryCode originCountry;
	private final Boolean adultRated;

	public MovieFilterImpl(Set<MovieGenre> genres, LocalDate releaseDateStart, LocalDate releaseDateEnd, CountryCode country, Boolean adult) {
		this.genres = genres;
		this.releaseDateStart = releaseDateStart;
		this.releaseDateEnd = releaseDateEnd;
		this.originCountry = country;
		this.adultRated = adult;
	}

	@Override
	public Set<MovieGenre> getGenres() {
		return this.genres;
	}

	@Override
	public LocalDate getReleaseDateStart() {
		return this.releaseDateStart;
	}

	@Override
	public LocalDate getReleaseDateEnd() {
		return this.releaseDateEnd;
	}

	@Override
	public CountryCode getOriginCountry() {
		return this.originCountry;
	}

	@Override
	public Boolean isAdultRated() {
		return this.adultRated;
	}
}
