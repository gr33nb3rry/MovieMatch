package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;

public interface MovieFilter {
	MovieGenre[] getGenres();

	MovieFilter setGenres(MovieGenre... genres);

	LocalDate getReleaseDateStart();

	MovieFilter setReleaseDateStart(LocalDate date);

	LocalDate getReleaseDateEnd();

	MovieFilter setReleaseDateEnd(LocalDate date);

	CountryCode getOriginCountry();

	MovieFilter setOriginCountry(CountryCode country);

	Boolean isAdultRated();

	MovieFilter setAdultRated(boolean rated);
}
