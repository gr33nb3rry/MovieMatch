package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Schedule {
    @JsonProperty("time")
    private String time;

    @JsonProperty("days")
    private List<String> days;
}