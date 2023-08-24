package org.moviematchers.moviematch.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.service.RandomQuoteService;

import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class RandomQuoteControllerTest {

    @Mock
    private RandomQuoteService service;
    private RandomQuoteController underTest;

    @BeforeEach
    void setUp() {
        underTest = new RandomQuoteController(service);}

    @Test
    void canGetRandomQuote() {
        // given
        // when
        underTest.getRandomQuote();
        // then
        verify(service).getRandomQuote();

    }

}