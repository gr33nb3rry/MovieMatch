package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;

import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;
import java.util.Set;

public interface MovieFilter {
	Set<MovieGenre> getGenres();

	Boolean isAdultRated();

	LocalDate getReleaseDateStart();

	LocalDate getReleaseDateEnd();


	CountryCode getOriginCountry();
}
