const basicAuth = "Basic YWRtaW46YWRtaW4";
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
    })
    .catch(err => console.error(err));
}