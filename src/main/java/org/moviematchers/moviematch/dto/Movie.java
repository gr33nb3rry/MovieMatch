package org.moviematchers.moviematch.dto;

import java.time.LocalDate;

public interface Movie {
	String getTitle();

	String getDescription();

	LocalDate getReleaseDate();

	double getRating();

	boolean isAdultRated();
}