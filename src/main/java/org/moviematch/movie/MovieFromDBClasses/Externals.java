package org.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Externals {
    @JsonProperty("tvrage")
    private int tvrage;

    @JsonProperty("thetvdb")
    private int thetvdb;

    @JsonProperty("imdb")
    private String imdb;
}
