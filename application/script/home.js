//const currentUserId;
getCurrentUserID();

function getCurrentUserID() {
    const apiUrl = `http://localhost:8080/user/getLoginID`;
    let headers = new Headers()
    headers.append("Authorization", "Basic QmlnUHJpbmNlOnRlc3Q=")
    fetch(apiUrl, { 
        //mode: 'no-cors',
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
