//const currentUserId;
getCurrentUserID();
const button = document.getElementById("button");
const popUp = document.getElementById("popUp");
const closePopUp = document.getElementById("closePopUp");

function getCurrentUserID() {
    const apiUrl = `http://localhost:8080/user/getLoginID`;
    let headers = new Headers()
    headers.append("Authorization", "Basic dXNlcjpwYXNzd29yZA")
    fetch(apiUrl, { 
        mode: 'no-cors',
        headers: headers }
    )
      .then(response => console.log(response))
      .catch(error => {
          console.error("ERROR:", error);
      });
}

function addFriend() {
    const userIdInvited = document.getElementById('user_id').value;
}

    myButton.addEventListener("click", function () {
        myPopup.classList.add("show");
    });
    closePopup.addEventListener("click", function () {
        myPopup.classList.remove("show");
    });

    window.addEventListener("click", function (event) {
        if (event.target == myPopup) {
            myPopup.classList.remove("show");
        }});