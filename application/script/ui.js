const ui = {};

ui.quote = {};

ui.quote.display = function(){
	client.getMovieQuote().then(quote => {
		const quoteElement = document.getElementById('movie-quote');
		quoteElement.innerHTML = `${quote.text}<br>${quote.movieTitle} (${quote.movieYear})`;
		quoteElement.classList.remove('invisible');
	})
}