package org.moviematchers.moviematch.mapper;

public interface Mapper<F, T> {
	T map(F value);
}
