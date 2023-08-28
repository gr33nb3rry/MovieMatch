package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.entity.Quote;
import org.moviematchers.moviematch.repository.RandomQuoteRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomQuoteServiceTest {
    @Mock
    private RandomQuoteRepository randomQuoteRepository;
    private RandomQuoteService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RandomQuoteService(randomQuoteRepository);
    }
    @Test
    void getRandomQuote() {
        // given
        long quoteCount = 10L;
        long randomId = 5L;
        Quote randomQuote = new Quote(randomId, "Quote text", "Movie title", 2023);
        when(randomQuoteRepository.count()).thenReturn(quoteCount);
        when(randomQuoteRepository.findById(anyLong())).thenReturn(Optional.of(randomQuote));
        // when
        Quote result = underTest.getRandomQuote();
        // then
        verify(randomQuoteRepository).count();
        verify(randomQuoteRepository).findById(anyLong());

        assertThat(result.getId()).isEqualTo(randomId);
    }
}