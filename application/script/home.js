const basicAuth = "Basic YWRtaW46YWRtaW4";
let userToken;
let userMainID = 6;
let friendlist = [];
let inviteFriendID;
let lastInviteSentID = 0;
let lastInviteGotID = 0;
let userInviteInitiatorID;
let sessionID;
let sessionUserID;

getToken();
getRandomQuote();
getFriends();
function getToken() {
    fetch('http://localhost:8080/authorization/token', {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })
    .then(response => response.json())
    .then(data => {
        console.log(data.token);
        userToken = "Bearer " + data.token;
    })
    .catch(err => console.error(err));
}

function loop() {   
  setTimeout(function() {
    if (sessionUserID !== 0 && sessionUserID !== 1) {
        checkForInvite();
        checkForSessionCreating();
    }
    
    loop();
  }, 3000)
}
loop();

function getRandomQuote(){
    const quote = document.getElementById('quote');

    fetch('http://localhost:8080/quote/random', {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })
    .then(response => response.json())
    .then(data => {
        quote.innerHTML = `${data.quoteText}<br>${data.movieTitle} (${data.movieYear})`;
    })
    .catch(err => console.error(err));
}

function getFriends() {
    friendlist = [];

    const friendshipsUrl = 'http://localhost:8080/friendship/byID?id=' + userMainID;
    fetch(friendshipsUrl, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })
    .then(response => response.json())
    .then(data => {

        for(let i = 0; i < data.length; i++) {
            let friendId;
            let friendUsername;
            
            if (data[i].user1ID.userID === userMainID) {
                friendId = data[i].user2ID.userID;
                friendUsername = data[i].user2ID.userName;
            }
            else {
                friendId = data[i].user1ID.userID;
                friendUsername = data[i].user1ID.userName;
            }
            friendlist.push([friendId, friendUsername]);
        }
        updateFriendlist();
    })
    .catch(err => console.error(err));
}

function updateFriendlist() {
    const friendlistContainer = document.getElementById("invite_friend_container");
    friendlistContainer.innerHTML = `<h2 id="header_up_friend" > Find movie with friend </h2>`;

    for(let i = 0; i < friendlist.length; i++) {
        const friend = 
        `
        <div class="friend">
            <div class="friend_left_side">
                <div class="friend_picture" style="background-color: white;">

                    <img src="asset/profile.png" class="header_button_icon" width="30px" height="30px" >
                </div>
                <p class="friend_name">${friendlist[i][1]}</p>
            </div>
            <a href="#popup-filters" onclick="setInviteFriendID(${friendlist[i][0]})"><div class="friend_picture" style="background-color: #faae2b;">
                <img src="asset/popcorn.png"  width="25px" height="30px">
            </div></a>
        </div>
        `
        friendlistContainer.innerHTML += friend;
    }
}

function getProfile() {
    const userUrl = 'http://localhost:8080/user/name?id=' + userMainID;
    const usernameElement = document.getElementById('popup-username');
    fetch(userUrl, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })   
    .then(response => response.text())
    .then((text) => {
        usernameElement.innerHTML = `<p>${text}</p>`;
    })
    .catch(err => console.error(err));
}



function getCollection() {
    
    refreshCollection();
    const url = 'http://localhost:8080/collection/byID?id=' + userMainID;
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })
    .then(response => response.json())
    .then(data => {
        for(let i = 0; i < data.length; i++) {
            let moviePoster;
            let userRating;
            
            userRating = data[i].userRating;
            fetch("http://localhost:8080/collection/fromDBbyName?movieTitle="+ data[i].movieTitle,{
                method: 'GET',
                headers: {
                    'Authorization': basicAuth
                },
            })
            .then(response => response.json())
            .then(data => {
                moviePoster = data.posterURL;
                console.log(data);
                updateCollection(moviePoster, userRating);
            })
            .catch(err => console.error(err));
        }    
    })
    .catch(err => console.error(err));
}

function refreshCollection(){
    const collectionContainer = document.getElementById('collection_movies');
    collectionContainer.innerHTML = "";
}

function updateCollection(poster, rating) {

    const collectionContainer = document.getElementById('collection_movies');
    console.log(poster);

    const movie = 
    `
        <div class="collection_movie">
            <img src="${poster}" width="150px">
            <div class="collection_movie_rating">${rating}</div>
        </div>
    `
    collectionContainer.innerHTML += movie;
}
function setInviteFriendID(friendId) {
    inviteFriendID = friendId;
}
function sendInvite() {
    const form = document.getElementById("movie_filters_form");
    const selectedGenres = Array.from(form.elements["genre"])
    .filter(checkbox => checkbox.checked)
    .map(checkbox => checkbox.value);
    const selectedDateStart = form.elements["start_year"].value;
    const selectedDateEnd = form.elements["end_year"].value;
    const selectedCountry = form.elements["country"].value;
    const selectedAgeRating = form.elements["age_rating"].value;
    
    const dateStart = new Date(selectedDateStart);
    const formattedDateStart = dateStart.toISOString().split('T')[0];
    const dateEnd = new Date(selectedDateEnd);
    const formattedDateEnd = dateEnd.toISOString().split('T')[0];

    const isMovieAdult = selectedAgeRating === "18plus";
  
    const inviteData = {
      userIDInitiator: userMainID,
      userIDInvited: inviteFriendID,
      movieGenres: selectedGenres,
      movieDateStart: formattedDateStart,
      movieDateEnd: formattedDateEnd,
      movieCountry: selectedCountry,
      isMovieAdult: isMovieAdult

    };
    const url = 'http://localhost:8080/invite';
    console.log(JSON.stringify(inviteData));

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        },
        body: JSON.stringify(inviteData)
    })
    .then(response => response.text())
    .then((text) => {
        lastInviteSentID = parseInt(text);
    })
    .catch(err => console.error(err));
  }

function checkForSessionCreating() {
    //method to fetch sessions by lastInviteID if that session exists -> join
    const url = 'http://localhost:8080/session/byInvite?invitationID='+lastInviteSentID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.text())
    .then((text) => {
        sessionID = parseInt(text);
        if (sessionID >= 0) joinSession();
    })
    .catch(err => console.error(err));
}
function checkForInvite() {
    const url = 'http://localhost:8080/invite/byID?id=' + userMainID;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.invitationID > lastInviteGotID) {
            userInviteInitiatorID = data.userIDInitiator;
            lastInviteGotID = data.invitationID;
            const currentUrl = window.location.href;
            if (currentUrl.endsWith('#')) {
                window.location.replace(currentUrl + 'popup-invite');
            }
            else {
                window.location.replace(currentUrl + '#popup-invite');
            }
            updateInvitePopup(data);
        }
    })
    .catch(err => console.error(err));
}
function updateInvitePopup(data) {
    fetch('http://localhost:8080/user/name?id=' + data.userIDInitiator, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })   
    .then(response => response.text())
    .then((text) => {
        const initiatorName = text;
        console.log(data);
        const popup = document.getElementById("popup-invite");
        const code = 
        `
        <div class="popup_container">
            <h1>Invite from ${initiatorName}</h1>
            <div id="popup_invite_filter_container">
                <div class="popup_invite_filter">
                    <h2>Genres</h2>
                    <p>${data.movieGenres}</p>
                </div>
                <div class="popup_invite_filter">
                    <h2>Country</h2>
                    <p>${data.movieCountry}</p>
                </div>
                <div class="popup_invite_filter">
                    <h2>Years</h2>
                    <p>${data.movieDateStart} - ${data.movieDateEnd}</p>
                </div>
                <div class="popup_invite_filter">
                    <h2>Age</h2>
                    <p>${data.isMovieAdult}</p>
                </div>
            </div>
            
            <a onclick="createSession()"><div class="popup_invite_join">Join</div></a>
            <a href="#">
                <div class="popup_close">
                    <img src="asset/close-cross.png" width="40px" height="40px">
                </div>
            </a>
        </div>
        `
        popup.innerHTML = code;
    })
    .catch(err => console.error(err));

    
}
function createSession() {
    const sessionData = {
        invitationID: lastInviteGotID,
        user1ID: userInviteInitiatorID,
        user2ID: userMainID
    };
    const url = 'http://localhost:8080/session/create';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        },
        body: JSON.stringify(sessionData)
    })
    .then(response => response.text())
    .then((text) => {
        sessionID = parseInt(text);
        joinSession();
    })
    .catch(err => console.error(err));
}
function joinSession() {
    const url = 'http://localhost:8080/session/join?sessionID='+sessionID+'&userID='+userMainID;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        }
    })
    .then(response => response.text())
    .then((text) => {
        sessionUserID = parseInt(text);
        window.location.replace(
            window.location.href.substring(0, window.location.href.lastIndexOf('/') + 1)
            + "session.html"
        );
        console.log("joined! new id is " + sessionUserID);
    })
    .catch(err => console.error(err));
}

function addCollection() {
    const movieTitleInput = document.getElementById('movie_title');
    const movieRatingInput = document.getElementById('movie_rating');
    const movieTitle = movieTitleInput.value;
    const movieRating = movieRatingInput.value;

    const url = 'http://localhost:8080/collection';
    const requestBody = {
        userID: {userID: userMainID},
        movieTitle: movieTitle,
        userRating: movieRating
    };
    
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => {
        if (response.ok) {
            console.log('Movie added successfully to collection.');
            getCollection();
        } else {
            console.error('Failed to add movie.');
        }
    })
    .catch(err => console.error(err));
}

function addFriend(){
    const friendIDInput = document.getElementById('add_friend_user_id');
    const friendID = friendIDInput.value;
    const addFriendUrl = 'http://localhost:8080/friendship/request';
    const requestUrl = {
        user1ID: {userID: userMainID},
        user2ID: {userID: friendID}
    };
    fetch(addFriendUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': basicAuth
        },
        body: JSON.stringify(requestUrl)  
    })
    .then(response => {
        if (response.ok) {
            console.log('successfully');
            getFriends();
        } else {
            console.error('Failed to add friend id.');
        }
    })
    .catch(err => console.error(err));
}

const changePasswordButton = document.getElementById('change_password_button');
changePasswordButton.addEventListener('click', openChangePasswordForm);

function openChangePasswordForm() {

    const passwordForm = document.getElementById('password_change_form');
    passwordForm.style.display = 'block';
}

function closePasswordChangeForm() {
    const passwordForm = document.getElementById('password_change_form');
    const newPasswordInput = document.getElementById('new_password');
    passwordForm.style.display = 'none';
    newPasswordInput.value = '';
}

function changePassword(){
    const newPassword = document.getElementById('new_password').value;
    fetch('http://localhost:8080/user/password?id=' + userMainID +'&value='+newPassword, {
        method: 'PUT',
        headers: {
            'Authorization': basicAuth,
            'Content-Type': 'application/json'
        },
    })
    .then(response => response.text())
    .then(text => {
        console.log(text);
    })
    .catch(err => console.error(err));
}