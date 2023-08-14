package org.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
    @JsonProperty("average")
    private double average;
}
