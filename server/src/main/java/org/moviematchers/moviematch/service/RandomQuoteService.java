package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.repository.RandomQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RandomQuoteService {
    private final RandomQuoteRepository randomQuoteRepository;
    @Autowired
    public RandomQuoteService(RandomQuoteRepository randomQuoteRepository) {
        this.randomQuoteRepository = randomQuoteRepository;
    }
    public List<Quote> getAllQuotes() {
        return randomQuoteRepository.findAll();
    }
    public Quote getRandomQuote() {
        long quoteCount = randomQuoteRepository.count();
        Random random = new Random();
        long id = random.nextLong(quoteCount - 1) + 1;
        Optional<Quote> quote = randomQuoteRepository.findById(id);
        return quote.orElseGet(Quote::new);

    }

    public void addQuote(Quote quote) {
        randomQuoteRepository.save(quote);
    }
}
