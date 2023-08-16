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
    @Column(name = "quoteID")
    private Long quoteID;
    @Column(name = "quoteText")
    private String quoteText;
    @Column(name = "movieTitle")
    private String movieTitle;
    @Column(name = "movieYear")
    private int movieYear;

    public Quote() {
    }

    public Quote(Long quoteID, String quoteText, String movieTitle, int movieYear) {
        this.quoteID = quoteID;
        this.quoteText = quoteText;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
    }

    public Long getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(Long quoteID) {
        this.quoteID = quoteID;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
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
