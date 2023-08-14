package com.moviematch.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebChannel {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("officialSite")
    private String officialSite;
}
