package org.moviematchers.moviematch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.moviematchers.moviematch.type.ErrorStatus;

import java.time.Instant;
import java.util.Collection;

public class ErrorResponseBodyImpl implements ErrorResponseBody {
	@JsonProperty("errors")
	private final Collection<ErrorStatus> statuses;

	private final Instant timestamp;

	public ErrorResponseBodyImpl(Collection<ErrorStatus> statuses, Instant timestamp) {
		this.statuses = statuses;
		this.timestamp = timestamp;
	}

	@Override
	public Collection<ErrorStatus> getStatuses() {
		return this.statuses;
	}

	@Override
	public Instant getTime() {
		return this.timestamp;
	}
}