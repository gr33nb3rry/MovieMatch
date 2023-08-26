client.user.identity.authorize();
const idPromise = client.user.getId();
let userId;
if (idPromise == null) {
  console.log("Failed to get ID");
}
idPromise.then(id => {
    userId = id;
})
let currentMovieDescription;
let currentMovieTitle;
let lastMatchMovieTitle = "";
let sessionFriendID = parseInt(localStorage.getItem("sessionFriendID"));
let sessionID = parseInt(localStorage.getItem("sessionID"));
let sessionUserID = parseInt(localStorage.getItem("sessionUserID"));
console.log(sessionID);
console.log(sessionUserID);

function getFriendName() {
    fetch(`http://localhost:8080/user/name?id=${sessionFriendID}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.text())
    .then(text => {
        displaySessionUsernames(text);
    })
    .catch(err => console.error(err));
}
function displaySessionUsernames(friendName) {
    const usernamesContainer = document.getElementById("session_usernames");
    usernamesContainer.innerHTML = `${client.user.getUsername()} and ${friendName}`;
}

function addMovie() {
    const url = 'http://localhost:8080/session/addMovies?sessionID='+sessionID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
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
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
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
function assignImageByGenre(data) {
    let randomGenre = Math.round(Math.random() * (data.length - 1) + 0);
    return `asset/genres/${data[randomGenre].toLowerCase()}.png`;
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
            <img src="${assignImageByGenre(data.genres)}">
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
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
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
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
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
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
        updateCurrentMovie(data);
    })
    .catch(err => console.error(err));
}

function checkForNewMatch() {
    const url = 'http://localhost:8080/session/lastMatch?sessionID='+sessionID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.title !== lastMatchMovieTitle) {
            lastMatchMovieTitle = data.title;
            console.log("New match!");
            getMatchCount();
            getNewMatch();
        }
    })
    .catch(err => console.error(err));
}
function getMatchCount() {
    const url = 'http://localhost:8080/session/matchCount?sessionID='+sessionID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.text())
    .then(text => {
        console.log(text);
        updateMatchCount(text);
    })
    .catch(err => console.error(err));
}
function updateMatchCount(text) {
    const matchCountContainer = document.getElementById("match_count");
    matchCountContainer.innerHTML = text;
}
function getNewMatch() {
    const url = 'http://localhost:8080/session/lastMatch?sessionID='+sessionID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
        showNewMatch(data);
    })
    .catch(err => console.error(err));
}
function showNewMatch(data) {
    const newMatchContainer = document.getElementById("match_container");
    const code = 
    `
    <div id="match_new_movie_container">
        <div id="match_new_movie">
            <img src="${data.posterURL}" width="150px">
        </div>
        New match!
    </div>
    `
    newMatchContainer.innerHTML += code;
    setTimeout(function() {
        hideNewMatch();
    }, 3000)
}
function hideNewMatch() {
    const newMatchContainer = document.getElementById("match_new_movie_container");
    newMatchContainer.remove();
}
function getAllMatches() {
    const url = 'http://localhost:8080/session/allMatches?sessionID='+sessionID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
        displayAllMatches(data);
    })
    .catch(err => console.error(err));
}
function displayAllMatches(data) {
    const matchesContainer = document.getElementById("match_movies");
    matchesContainer.innerHTML = "";
    for (let i = 0; i < data.length; i++) {
        const code =
        `
        <div class="match_movie">
            <img src="${data[i].posterURL}" width="150px">
            <div class="match_movie_rating">${data[i].rating}</div>
        </div>
        `
        matchesContainer.innerHTML += code;
    }
}
function getHistory() {
    const url = 'http://localhost:8080/session/likedMovies?sessionID='+sessionID+'&userNumber='+sessionUserID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
        }
    })
    .then(response => response.json())
    .then(data => {
        displayHistory(data);
    })
    .catch(err => console.error(err));
}
function displayHistory(data) {
    const historyContainer = document.getElementById("history_movies");
    historyContainer.innerHTML = "";
    for (let i = 0; i < data.length; i++) {
        const code =
        `
        <div class="history_movie">
            <img src="${data[i].posterURL}" width="150px">
            <div class="history_movie_rating">${data[i].rating}</div>
        </div>
        `
        historyContainer.innerHTML += code;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    getFriendName();
    addMovie();
})
function loop() {   
    setTimeout(function() {
        checkForNewMatch();
      
      loop();
    }, 3000)
  }
  loop();