package com.ruslanlapka.randllove.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies() {return movieService.getMovies();}

    @GetMapping(path = "db")
    public MovieFromDB getMovieFromDB(@RequestParam(required = true) String id) throws IOException, InterruptedException {
        return movieService.getMovieFromDB(id);
    }


    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }
}
