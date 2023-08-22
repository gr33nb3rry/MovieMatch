const basicAuth = "Basic YWRtaW46YWRtaW4";
let userToken;
let userID = 6;
let friendlist = [];

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

    const friendshipsUrl = 'http://localhost:8080/friendship/byID?id=' + userID;
    fetch(friendshipsUrl, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);

        for(let i = 0; i < data.length; i++) {
            let friendId;
            let friendUsername;
            
            if (data[i].user1ID.userID === userID) {
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
            <a href="#popup-filters"><div class="friend_picture" style="background-color: #faae2b;">
                <img src="asset/popcorn.png"  width="25px" height="30px">
            </div></a>
        </div>
        `
        friendlistContainer.innerHTML += friend;
    }
}

function getProfile() {
    const userUrl = 'http://localhost:8080/user/name?id=' + userID;
    const usernameElement = document.getElementById('popup-username');
    fetch(userUrl, {
        method: 'GET',
        headers: {
            'Authorization': basicAuth
        },
    })   
    .then(response => response.text())
    .then((text) => {
        console.log(text);
        usernameElement.innerHTML = `<p>${text}</p>`;
    })
    .catch(err => console.error(err));
}



function getCollection() {
    
    refreshCollection();
    const url = 'http://localhost:8080/collection/byID?id=4';
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


