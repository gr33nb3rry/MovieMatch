package org.moviematchers.moviematch.dto;

import org.moviematchers.moviematch.type.MovieGenre;

import java.time.LocalDate;
import java.util.Set;
import java.net.URL;

public interface Movie {
	String getTitle();

	String getDescription();

	LocalDate getReleaseDate();

	Double getRating();

	Set<MovieGenre> getGenres();

	Boolean isAdultRated();

	URL getPosterURL();
}