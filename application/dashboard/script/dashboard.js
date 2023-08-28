client.user.authorize();
let userId = client.user.getId();
let friendlist = [];
let inviteFriendID;
let lastInviteSentID = 0;
let lastInviteGotID = 0;
let userInviteInitiatorID;
let sessionID;
let sessionUserID;

function displayRandomQuote(){
    const quoteElement = document.getElementById('quote');

    client.getMovieQuote()
    .then(quote => {
        quoteElement.innerHTML = `${quote.text}<br>${quote.movieTitle} (${quote.movieYear})`;
    })}
function fillFriendlist(list) {
    friendlist = list;
}
function displayFriends() {
    client.user.getFriends().then(friendList => {
        fillFriendlist(friendList);
        const friendListContainer = document.getElementById("invite_friend_container");
        friendListContainer.innerHTML = `<h2 id="header_up_friend" > Find movie with friend </h2>`;

        for (let i = 0; i < friendList.length; i++) {
            const friend =
                `
            <div class="friend">
                <div class="friend_left_side">
                    <div class="friend_picture" style="background-color: white;">
    
                        <img src="../asset/icon/profile.svg" class="header_button_icon" width="30px" height="30px" >
                    </div>
                    <p class="friend_name">${friendList[i][1]}</p>
                </div>
                <a href="#popup-filters" onclick="setInviteFriendID(${friendList[i][0]})"><div class="friend_picture" style="background-color: #faae2b;">
                    <img src="asset/session.png"  width="25px" height="30px">
                </div></a>
            </div>
            `
            friendListContainer.innerHTML += friend;
        }
    });
}
function addFriend(){
    const friendIDInput = document.getElementById('add_friend_user_id');
    const friendID = friendIDInput.value;
    const addFriendUrl = 'http://localhost:8080/friendship/request';
    const requestUrl = {
        firstUserEntity: {id: userId},
        secondUserEntity: {id: friendID}
    };
    fetch(addFriendUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
        },
        body: JSON.stringify(requestUrl)  
    })
    .then(response => {
        if (response.ok) {
            console.log('successfully');
            displayFriends();
        } else {
            console.error('Failed to add friend id.');
        }
    })
    .catch(err => console.error(err));
}

function setInviteFriendID(friendId) {
    inviteFriendID = friendId;
    console.log(inviteFriendID);
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
      userIDInitiator: userId,
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
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
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
    if (lastInviteSentID > 0) {
        const url = 'http://localhost:8080/session/byInvite?invitationID='+lastInviteSentID;

        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
            }
        })
        .then(response => response.text())
        .then((text) => {
            sessionID = parseInt(text);
            if (sessionID >= 0) joinSession();
        })
        .catch(err => console.error(err));
    }
}
function checkForInvite() {
    const url = 'http://localhost:8080/invite/byID?id=' + userId;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
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
function deleteInvitation() {
    const url = 'http://localhost:8080/invite?id=' + lastInviteGotID;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
        }
    })
    .then(response => response.text())
    .then((text) => {
        console.log(text);
    })
    .catch(err => console.error(err));
}
function updateInvitePopup(data) {
    fetch('http://localhost:8080/user/name?id=' + data.userIDInitiator, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
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
            <a href="#" onclick="deleteInvitation()">
                <div class="popup_close">
                    <img src="../asset/close.png" width="40px" height="40px">
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
        user2ID: userId
    };
    const url = 'http://localhost:8080/session/create';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
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
    const url = 'http://localhost:8080/session/join?sessionID='+sessionID+'&userID='+userId;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
        }
    })
    .then(response => response.text())
    .then((text) => {
        sessionUserID = parseInt(text);

        localStorage.setItem('sessionID', sessionID);
        localStorage.setItem('sessionUserID', sessionUserID);

        window.location.replace(client.configuration.path.session);
        console.log("joined! new id is " + sessionUserID);
    })
    .catch(err => console.error(err));
}
let changePasswordButton;

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
    fetch('http://localhost:8080/user/password?id='+userId+'&value='+newPassword, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.getAuthorizationToken().value}`
        },
    })
    .then(response => response.text())
    .then(text => {
        console.log(text);
    })
    .catch(err => console.error(err));
}
document.addEventListener('DOMContentLoaded', () => {
    displayRandomQuote();
    displayFriends();
    changePasswordButton = document.getElementById('change_password_button');
    changePasswordButton.addEventListener('click', openChangePasswordForm);
})
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