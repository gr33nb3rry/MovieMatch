package com.moviematch.moviematch.movie;

import com.moviematch.moviematch.movie.MovieFromDBClasses.MovieFromDB;
import com.moviematch.moviematch.movie.MovieFromDBClasses.MovieFromDBSeries;
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

    @GetMapping(path = "series")
    public MovieFromDBSeries getMovieFromDBseries(@RequestParam(required = true) String id) throws IOException, InterruptedException {
        return movieService.getMovieFromDBseries(id);
    }
    @GetMapping(path = "movie")
    public MovieFromDB getMovieFromDBmovie(@RequestParam(required = true) String id) throws IOException, InterruptedException {
        return movieService.getMovieFromDBmovie(id);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @DeleteMapping
    public void deleteMovie(@RequestParam(required = true) Long id) {
        movieService.deleteMovie(id);
    }
}
