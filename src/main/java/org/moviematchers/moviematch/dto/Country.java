package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("timezone")
    private String timezone;
}