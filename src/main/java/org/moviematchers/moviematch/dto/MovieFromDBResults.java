package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieFromDBResults {
    @JsonProperty("movie_results")
    private List<MovieFromDB> movieResults;

    public MovieFromDBResults() {
    }

    public MovieFromDBResults(List<MovieFromDB> movieResults) {
        this.movieResults = movieResults;
    }

    public List<MovieFromDB> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<MovieFromDB> movieResults) {
        this.movieResults = movieResults;
    }
}