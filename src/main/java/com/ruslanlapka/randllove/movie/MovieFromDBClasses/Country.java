package com.ruslanlapka.randllove.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("timezone")
    private String timezone;
}
