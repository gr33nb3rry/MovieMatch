package com.moviematch.moviematch.movie.MovieFromDBClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image {
    @JsonProperty("medium")
    private String medium;

    @JsonProperty("original")
    private String original;

    public Image() {
    }

    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
