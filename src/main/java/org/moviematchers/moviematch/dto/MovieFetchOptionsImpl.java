package org.moviematchers.moviematch.dto;

public class MovieFetchOptionsImpl implements MovieFetchOptions {
	private Long page;

	@Override
	public MovieFetchOptionsImpl setPage(long page) {
		this.page = page;
		return this;
	}

	@Override
	public Long getPage() {
		return this.page;
	}
}