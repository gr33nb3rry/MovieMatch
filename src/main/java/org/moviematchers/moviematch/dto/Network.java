package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Network {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private Country country;

    @JsonProperty("officialSite")
    private String officialSite;
}
