package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;

public class MovieFilterImpl implements MovieFilter {
	private MovieGenre[] genres;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private CountryCode country;
	private Boolean adult;

	@Override
	public MovieGenre[] getGenres() {
		return this.genres;
	}

	@Override
	public MovieFilter setGenres(MovieGenre... genres) {
		this.genres = genres;
		return this;
	}

	@Override
	public LocalDate getReleaseDateStart() {
		return this.dateStart;
	}

	@Override
	public MovieFilter setReleaseDateStart(LocalDate date) {
		this.dateStart = date;
		return this;
	}

	@Override
	public LocalDate getReleaseDateEnd() {
		return this.dateEnd;
	}

	@Override
	public MovieFilter setReleaseDateEnd(LocalDate date) {
		this.dateEnd = date;
		return this;
	}

	@Override
	public CountryCode getOriginCountry() {
		return this.country;
	}

	@Override
	public MovieFilter setOriginCountry(CountryCode country) {
		this.country = country;
		return this;
	}

	@Override
	public Boolean isAdultRated() {
		return this.adult;
	}

	@Override
	public MovieFilter setAdultRated(boolean rated) {
		this.adult = rated;
		return this;
	}
}
