package org.moviematchers.moviematch.dto;

import java.net.URL;
import java.time.LocalDate;

public interface Movie {
	String getTitle();

	String getDescription();

	LocalDate getReleaseDate();

	Double getRating();

	Boolean isAdultRated();

	URL getPosterURL();
}