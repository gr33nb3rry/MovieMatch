const basicAuth = "Basic YWRtaW46YWRtaW4";
let currentMovieDescription;
let currentMovieTitle;
let userMainID = 6;
let sessionID = parseInt(localStorage.getItem("sessionID"));
let sessionUserID = parseInt(localStorage.getItem("sessionUserID"));
console.log(sessionID);
console.log(sessionUserID);

addMovie();

function addMovie() {
    const url = 'http://localhost:8080/session/addMovies?sessionID='+sessionID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.text())
    .then((text) => {
        console.log(text);
        getCurrentMovie();
    })
    .catch(err => console.error(err));
}
function getCurrentMovie() {
    const url = 'http://localhost:8080/session/getCurrent?sessionID='+sessionID+'&userNumber='+sessionUserID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        updateCurrentMovie(data);
    })
    .catch(err => console.error(err));
}
function getShortDescription(description) {
    if (description.length <= 110) {
        return description;
    } else {
        return description.substring(0, 107) + '...';
    }
}
function getLongDescription(description) {
    if (description.length <= 300) {
        return description;
    } else {
        return description.substring(0, 297) + '...';
    }
}
function updateCurrentMovie(data) {
    currentMovieDescription = data.description;
    currentMovieTitle = data.title;
    const movie = document.getElementById("main_movie");
    const code = 
    `
    <img class="main_movie_image" src="${data.posterURL}">
    <div id="main_movie_text">
        <p style="text-align: center;opacity:50%;">click to read more</p>
        <p>${getShortDescription(data.description)}</p>
        <h2>${data.title}</h2>
    </div>
    <div id="main_movie_upper">
        <div class="main_movie_rating">${data.rating}</div>
        <div class="main_movie_genre">
            <img src="asset/heart.png">
        </div>
    </div>
    `
    movie.innerHTML = code;
}
function showMoreMovieinfo() {
    const text = document.getElementById("main_movie_text");
    const code = 
    `
    <p style="text-align: center;opacity:50%;">click to read more</p>
    <p>${getLongDescription(currentMovieDescription)}</p>
    <h2>${currentMovieTitle}</h2>
    `
    text.innerHTML = code;
}
function likeMovie() {
    const url = 'http://localhost:8080/session/like?sessionID='+sessionID+'&userNumber='+sessionUserID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.json())
    .then(data => {
        updateCurrentMovie(data);
    })
    .catch(err => console.error(err));
}
function skipMovie() {
    const url = 'http://localhost:8080/session/skip?sessionID='+sessionID+'&userNumber='+sessionUserID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.json())
    .then(data => {
        updateCurrentMovie(data);
    })
    .catch(err => console.error(err));
}
function returnMovie() {
    const url = 'http://localhost:8080/session/return?sessionID='+sessionID+'&userNumber='+sessionUserID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.json())
    .then(data => {
        updateCurrentMovie(data);
    })
    .catch(err => console.error(err));
}


///For genres icons change by movies

async function fetchMovieData(movieId) {
    const response = await fetch(`?????${movieId}`);
    const data = await response.json();
    return data;
}


async function assignImageByGenre() {
    const code ='';
    const movieData = await fetchMovieData(movieId);
    const genre = movieData.genre;

    switch (genre) {
        case 'ACTION':
             code = '<img src="asset/genres/action.png">';
            break;
        case 'ADVENTURE':
             code = '<img src="asset/genres/adventure.png">';
            break;
        case 'ANIMATION':
             code = '<img src="asset/genres/animation.png">';
            break;
        case 'COMEDY':
             code = '<img src="asset/genres/comendy.png">';
            break;
        case 'CRIME':
             code = '<img src="asset/genres/crime.png">';
            break;
        case 'DOCUMENTARY':
             code = '<img src="asset/genres/documentory.png">';
            break;
        case 'DRAMA':
             code = '<img src="asset/genres/drama.png">';
            break;
        case 'FAMILY':
            const code = '<img src="asset/genres/family.png">';
            break;
        case 'FANTASY':
             code = '<img src="asset/genres/fantasy.png">';
            break;
        case 'HISTORY':
             code = '<img src="asset/genres/history.png">';
            break;
        case 'HORROR':
             code = '<img src="asset/genres/horror.png">';
            break;
        case 'MUSIC':
             code = '<img src="asset/genres/music.png">';
            break;
        case 'MYSTERY':
             code = '<img src="asset/genres/mystery.png">';
            break;
        case 'ROMANCE':
             code = '<img src="asset/genres/romance.png">';
            break;
        case 'SCIENCE_FICTION':
             code = '<img src="asset/genres/BLOOOOO.png">';
            //  one missing
            break;
        case 'TV':
             code = '<img src="asset/genres/tv.png">';
            break;
        case 'THRILLER':
             code = '<img src="asset/genres/thriller.png">';
            break;
        case 'WAR':
             code = '<img src="asset/genres/war.png">';
            break;
        case 'WESTERN':
             code = '<img src="asset/genres/western.png">';
            break;
        default:
            log.console('Error');
            break;
    }
   
}
