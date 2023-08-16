package org.moviematchers.moviematch.dto;

import java.time.LocalDate;

public interface Movie {
	String getTitle();

	String getDescription();

	LocalDate getReleaseDate();

	Double getRating();

	Boolean isAdultRated();
}