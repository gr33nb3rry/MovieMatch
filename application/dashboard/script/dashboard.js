client.user.identity.authorize();

function displayRandomQuote(){
    const quoteElement = document.getElementById('quote');

    client.getMovieQuote()
    .then(quote => {
        quoteElement.innerHTML = `${quote.text}<br>${quote.movieTitle} (${quote.movieYear})`;
    })}

function displayFriends() {
    client.user.getFriends().then(friendList => {
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
                <a href="#popup-filters"><div class="friend_picture" style="background-color: #faae2b;">
                    <img src="asset/session.png"  width="25px" height="30px">
                </div></a>
            </div>
            `
            friendListContainer.innerHTML += friend;
        }
    });
}

document.addEventListener('DOMContentLoaded', () => {
    displayRandomQuote();
    displayFriends();
})