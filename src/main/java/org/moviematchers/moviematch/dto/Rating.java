package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
    @JsonProperty("average")
    private double average;
}
