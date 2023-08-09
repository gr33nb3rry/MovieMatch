package com.ruslanlapka.randllove.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public MovieService (MovieRepository movieRepository, RestTemplate restTemplate) {

        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
    public void addMovie(Movie movie){
        movieRepository.save(movie);
    }

    public MovieFromDB getMovieFromDB(String id) throws IOException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOTYzODc0YWVhMGQxNjQ4ZWQ2YWVkMTVlMWY4MzdiZiIsInN1YiI6IjY0ZDM3NGY4YjZjMjY0MTE1NzUxN2M4ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.v-GKyHn0c4_BYL2Tt_lGTPHEQ4x3X3YLO_w1y2RbNLE");

        String url = "https://api.themoviedb.org/3/find/" + UriUtils.encodeQueryParam(id, StandardCharsets.UTF_8) +
                "?external_source=imdb_id";

        ResponseEntity<MovieFromDBResults> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MovieFromDBResults.class
        );

        MovieFromDBResults movie = responseEntity.getBody();

        assert movie != null;
        return movie.getMovieResults().get(0);
    }
}
