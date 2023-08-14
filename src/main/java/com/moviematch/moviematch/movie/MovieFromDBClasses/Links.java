package com.moviematch.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {
    @JsonProperty("self")
    private Self self;

    @JsonProperty("previousepisode")
    private Previousepisode previousepisode;
}
