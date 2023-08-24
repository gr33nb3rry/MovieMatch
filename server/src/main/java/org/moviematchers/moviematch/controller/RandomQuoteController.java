package org.moviematchers.moviematch.controller;

import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.service.RandomQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "quote")
public class RandomQuoteController {
    private final RandomQuoteService randomQuoteService;
    @Autowired
    public RandomQuoteController(RandomQuoteService randomQuoteService) {
        this.randomQuoteService = randomQuoteService;
    }

    @GetMapping("random")
    public Quote getRandomQuote() {
        return randomQuoteService.getRandomQuote();
    }
}
