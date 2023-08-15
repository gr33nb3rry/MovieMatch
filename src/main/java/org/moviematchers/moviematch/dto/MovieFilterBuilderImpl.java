package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;
import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MovieFilterBuilderImpl implements MovieFilterBuilder {
	private Set<MovieGenre> genres;
	private Boolean adultRated;
	private LocalDate releaseDateStart;
	private LocalDate releaseDateEnd;
	private CountryCode originCountry;

	@Override
	public MovieFilterBuilderImpl genres(MovieGenre... genres) {
		if (this.genres == null) {
			this.genres = new HashSet<>();
		}
		this.genres.addAll(Arrays.asList(genres));
		return this;
	}

	@Override
	public MovieFilterBuilderImpl adultRated(boolean state) {
		this.adultRated = state;
		return this;
	}

	@Override
	public MovieFilterBuilderImpl releaseDateStart(LocalDate date) {
		this.releaseDateStart = date;
		return this;
	}

	@Override
	public MovieFilterBuilderImpl releaseDateEnd(LocalDate date) {
		this.releaseDateEnd = date;
		return this;
	}

	@Override
	public MovieFilterBuilderImpl originCountry(CountryCode code) {
		this.originCountry = code;
		return this;
	}

	@Override
	public MovieFilter build() {
		return new MovieFilterImpl(this.genres, this.releaseDateStart, this.releaseDateEnd, this.originCountry, this.adultRated);
	}
}
