function getProfile() {
    const usernameElement = document.getElementById('popup-username');
    const idElement = document.getElementById("popup_profile_id");
    usernameElement.innerHTML = `<p>${client.user.getUsername()}</p>`;
    const id = client.user.getId();
    idElement.innerHTML = `ID: ${id}`;
}
function getCollection() {
    refreshCollection();
    console.log(client.user.getId());
    const url = 'http://localhost:8080/collection/byID?id=' + userId;
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
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
                    'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
                },
            })
            .then(response => response.json())
            .then(data => {
                moviePoster = data.posterURL;
                updateCollection(moviePoster, userRating);
            })
            .catch(err => console.error(err));
        }    
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
        userID: {userID: userId},
        movieTitle: movieTitle,
        userRating: movieRating
    };
    
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${client.user.identity.getAuthorizationToken().value}`
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

function refreshCollection(){
    const collectionContainer = document.getElementById('collection_movies');
    collectionContainer.innerHTML = "";
}
function updateCollection(poster, rating) {

    const collectionContainer = document.getElementById('collection_movies');

    const movie = 
    `
        <div class="collection_movie">
            <img src="${poster}" width="150px">
            <div class="collection_movie_rating">${rating}</div>
        </div>
    `
    collectionContainer.innerHTML += movie;
}