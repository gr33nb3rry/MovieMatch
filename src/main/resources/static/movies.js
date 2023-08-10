const movies = [];
let showMovie = "all";
let hostName = "https://r-and-l-love.onrender.com";

getMovies();

function getMovies(){

    const apiUrl = hostName+`/movie`;

    fetch(apiUrl, { mode: 'cors' })
    .then(response => response.json())
    .then(data => {
        for (let i = 0; i < data.length; i++) {
            const imdbID = data[i].imdbID;
            const hisRating = data[i].hisRating;
            const herRating = data[i].herRating;
            const watchedDate = data[i].watchedDate;
            const type = data[i].type;

            if (type === "movie") {
                getMovie(imdbID, hisRating, herRating, watchedDate);
            }
            else {
                getSeries(imdbID, hisRating, herRating, watchedDate);
            }
            
        } 
        console.log("movies length: " + movies.length);
        
    })
    .catch(error => {
        console.error("ERROR:", error);
    });
}
function getMovie(imdbID, hisRating, herRating, watchedDate) {
    const apiUrl = hostName+`/movie/movie?id=${encodeURIComponent(imdbID)}`;
    fetch(apiUrl, { mode: 'cors' })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        const tmp = [
            data.title,
            "https://image.tmdb.org/t/p/original/" + data.poster_path,
            hisRating, 
            herRating, 
            data.type,
            watchedDate
        ];
        console.log(tmp);
        movies.push(tmp);

        pasteMovies();
        pasteShowMovieNumber();

    })
    .catch(error => {
        console.error("ERROR:", error);
    });
}
function getSeries(imdbID, hisRating, herRating, watchedDate) {
    const apiUrl = `http://localhost:8080/movie/series?id=${encodeURIComponent(imdbID)}`;
    fetch(apiUrl, { mode: 'cors' })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        const tmp = [
            data.name,
            data.image.medium,
            hisRating, 
            herRating, 
            data.type,
            watchedDate
        ];
        console.log(tmp);
        movies.push(tmp);

        pasteMovies();
        pasteShowMovieNumber();

    })
    .catch(error => {
        console.error("ERROR:", error);
    });
}
function pasteMovies(){
    document.getElementById("content_movies_container").innerHTML = "";

    let color1;
    let color2;

    let movie;
    for (let i = 0; i < movies.length; i++){
        if (movies[i][2] > 8.5) color1 = "#ffe400";
        else if (movies[i][2] > 6.5) color1 = "#7aff00";
        else if (movies[i][2] > 4) color1 = "#ff9300";
        else color1 = "#fe0000";

        if (movies[i][3] > 8.5) color2 = "#ffe400";
        else if (movies[i][3] > 6.5) color2 = "#7aff00";
        else if (movies[i][3] > 4) color2 = "#ff9300";
        else color2 = "#fe0000";
        
        movie = `<div class="content_movie">
        <div style="overflow:hidden;">
        <a href=""><img src="${movies[i][1]}" class="movie_image"></a>
        </div>
        <p class="movie_date">
            ${movies[i][5]}
        </p>
        <div class="movie_info">
            <p class="movie_title">${movies[i][0]}</p>
            <div class="movie_rating">
                <div class="movie_rating_container unselectable" style="color:${color1};">
                    <img src="https://gr33nb3rry.github.io/r-and-l-love/img/man_emoji.png" width="50px">${movies[i][2]}
                </div>
                <div class="movie_rating_container unselectable" style="color:${color2};">${movies[i][3]}
                    <img src="https://gr33nb3rry.github.io/r-and-l-love/img/girl_emoji.png" width="50px">
                </div>
            </div>
        </div>
        </div>`;
        if (showMovie == "all") {
            document.getElementById("content_movies_container").innerHTML += movie;
        }
        else {
            if (movies[i][4] == showMovie) {
                document.getElementById("content_movies_container").innerHTML += movie;
            }
        }
    }
}

function addMovie() {
    const movieID = document.getElementById('movieID').value;
    const movieDate = document.getElementById('movieDate').value;
    const hisRating = document.getElementById('hisRating').value;
    const herRating = document.getElementById('herRating').value;
    const selectedType = document.getElementById('movieType').value;

    // Construct the student data object with correct property names
    const userData = {
        imdbID: movieID,
        watchedDate: movieDate,
        hisRating: hisRating,
        herRating: herRating,
        type: selectedType,
    };
    const xhr = new XMLHttpRequest();
    
    xhr.open('POST', hostName+'/movie');
    xhr.setRequestHeader('Content-Type', 'application/json');
    
    xhr.onload = function() {
        if (xhr.status === 200) {
        alert('Movie added successfully!');
        document.getElementById('userForm').reset();

        } else {
        alert('Failed to add movie. Please try again.');
        }
    };
    
    xhr.onerror = function() {
        alert('An error occurred while adding the movie.');
        console.error('Error:', xhr.status);
    };
    
    xhr.send(JSON.stringify(userData));
}

function setShowMovie(genre) {
    showMovie = genre;
    pasteMovies();
}
function sortByRating(){
    for (let i = 0; i < movies.length - 1; i++){
        for (let j = 0; j < movies.length - i - 1; j++){
            if ((movies[j][2] + movies[j][3])/2 < (movies[j + 1][2] + movies[j + 1][3])/2){

                var temp = movies[j];
                movies[j] = movies[j + 1];
                movies[j + 1] = temp;

            }
        }
    }
    pasteMovies();
}
function sortByDate(){
    for (let i = 0; i < movies.length - 1; i++){
        for (let j = 0; j < movies.length - i - 1; j++){
            let date1 = "" + movies[j][5][8] + movies[j][5][9] 
                    + movies[j][5][3] + movies[j][5][4] 
                    + movies[j][5][0] + movies[j][5][1];
            let date2 = "" + movies[j + 1][5][8] + movies[j + 1][5][9] 
                    + movies[j + 1][5][3] + movies[j + 1][5][4] 
                    + movies[j + 1][5][0] + movies[j + 1][5][1];

            if (date1 > date2){
                var temp = movies[j];
                movies[j] = movies[j + 1];
                movies[j + 1] = temp;
            }
        }
    }
    pasteMovies();
}
function sortByName(){
    for (let i = 0; i < movies.length - 1; i++){
        for (let j = 0; j < movies.length - i - 1; j++){
            if (movies[j][0] > movies[j + 1][0]){

                var temp = movies[j];
                movies[j] = movies[j + 1];
                movies[j + 1] = temp;

            }
        }
    }
    pasteMovies();
}