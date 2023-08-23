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