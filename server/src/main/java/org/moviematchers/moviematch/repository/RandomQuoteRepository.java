package org.moviematchers.moviematch.repository;

import org.moviematchers.moviematch.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomQuoteRepository extends JpaRepository<Quote, Long> {
}
