package org.moviematchers.moviematch.dto;

import org.moviematchers.moviematch.type.ErrorStatus;

import java.time.Instant;
import java.util.Collection;

public interface ErrorResponseBody {
	Collection<ErrorStatus> getStatuses();

	Instant getTime();
}
