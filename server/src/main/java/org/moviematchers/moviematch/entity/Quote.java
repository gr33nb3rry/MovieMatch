package org.moviematchers.moviematch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movie_quote")
public class Quote {
    @Id
    @SequenceGenerator(
        name = "quote_sequence",
        sequenceName = "quote_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "quote_sequence"
    )
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "movie_title")
    private String movieTitle;
    @Column(name = "movie_year")
    private int movieYear;

    public Quote() {
    }

    public Quote(Long id, String text, String movieTitle, int movieYear) {
        this.id = id;
        this.text = text;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }
}
