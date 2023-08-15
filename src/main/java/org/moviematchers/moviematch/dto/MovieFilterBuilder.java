package org.moviematchers.moviematch.dto;

import com.neovisionaries.i18n.CountryCode;
import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;

public interface MovieFilterBuilder {
	MovieFilterBuilder genres(MovieGenre... genres);

	MovieFilterBuilder adultRated(boolean state);

	MovieFilterBuilder releaseDateStart(LocalDate date);

	MovieFilterBuilder releaseDateEnd(LocalDate date);

	MovieFilterBuilder originCountry(CountryCode code);

	MovieFilter build();
}
