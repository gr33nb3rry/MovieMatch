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
            <img src="${assignImageByGenre()}">
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

 function assignImageByGenre(date) {
    let code ='';
    const genre = date.genre[0];
    switch (genre) {
        case 'ACTION':
            code = 'asset/genres/action.png';
            break;
        case 'ADVENTURE':
            code = 'asset/genres/adventure.png';
            break;
        case 'ANIMATION':
            code = 'asset/genres/animation.png';
            break;
        case 'COMEDY':
            code = 'asset/genres/comendy.png';
            break;
        case 'CRIME':
            code = 'asset/genres/crime.png';
            break;
        case 'DOCUMENTARY':
            code = 'asset/genres/documentory.png';
            break;
        case 'DRAMA':
            code = 'asset/genres/drama.png';
            break;
        case 'FAMILY':
            const code = 'asset/genres/family.png';
            break;
        case 'FANTASY':
            code = 'asset/genres/fantasy.png';
            break;
        case 'HISTORY':
            code = 'asset/genres/history.png';
            break;
        case 'HORROR':
            code = 'asset/genres/horror.png';
            break;
        case 'MUSIC':
            code = 'asset/genres/music.png';
            break;
        case 'MYSTERY':
            code = 'asset/genres/mystery.png';
            break;
        case 'ROMANCE':
            code = 'asset/genres/romance.png';
            break;
        case 'SCIENCE_FICTION':
            code = 'asset/genres/history.png';  
            break;
        case 'TV':
            code = 'asset/genres/tv.png';
            break;
        case 'THRILLER':
            code = 'asset/genres/thriller.png';
            break;
        case 'WAR':
            code = 'asset/genres/war.png';
            break;
        case 'WESTERN':
            code = 'asset/genres/western.png';
            break;
        default:
            code = 'asset/genres/family.png';
            break;
    }
}
