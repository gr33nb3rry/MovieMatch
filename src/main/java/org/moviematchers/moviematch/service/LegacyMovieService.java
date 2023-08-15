package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.dto.MovieFromDBResults;
import org.moviematchers.moviematch.entity.Movie;
import org.moviematchers.moviematch.dto.MovieFromDB;
import org.moviematchers.moviematch.dto.MovieFromDBSeries;
import org.moviematchers.moviematch.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LegacyMovieService {
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public LegacyMovieService(MovieRepository movieRepository, RestTemplate restTemplate) {

        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
    public void addMovie(Movie movie){
        movieRepository.save(movie);
    }

    public MovieFromDBSeries getMovieFromDBseries(String id) throws IOException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://api.tvmaze.com/lookup/shows?imdb=" + UriUtils.encodeQueryParam(id, StandardCharsets.UTF_8);

        ResponseEntity<MovieFromDBSeries> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                MovieFromDBSeries.class
        );

        MovieFromDBSeries movie = responseEntity.getBody();

        return movie;
    }
    public MovieFromDB getMovieFromDBmovie(String id) throws IOException, InterruptedException {

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

    public void deleteMovie(Long id) {
        boolean exists = movieRepository.existsById(id);
        if (!exists)
            throw new IllegalStateException("Movie with ID " + id + " doesn't exist");
        movieRepository.deleteById(id);
    }
}
