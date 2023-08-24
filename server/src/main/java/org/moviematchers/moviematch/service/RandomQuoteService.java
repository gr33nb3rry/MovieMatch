package org.moviematchers.moviematch.service;

import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.repository.RandomQuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class RandomQuoteService {
    private final Logger logger = LoggerFactory.getLogger(RandomQuoteService.class);
    private final RandomQuoteRepository randomQuoteRepository;
    @Autowired
    public RandomQuoteService(RandomQuoteRepository randomQuoteRepository) {
        this.randomQuoteRepository = randomQuoteRepository;
    }
    public Quote getRandomQuote() {
        Random random = new Random();
        long quoteCount = randomQuoteRepository.count();
        long id = random.nextLong(quoteCount - 1) + 1;
        logger.info("Total quotes in repository: {}", quoteCount);
        logger.info("ID of random quote: {}", quoteCount);
        Optional<Quote> quote = randomQuoteRepository.findById(id);
        return quote.orElseGet(Quote::new);

    }
}
