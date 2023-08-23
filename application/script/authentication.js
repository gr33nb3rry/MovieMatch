// COMMUNICATION LOGIC

const authentication = {
    serverURL: 'http://localhost:8080'
};

authentication.authenticate = function(username, password) {
    return fetch(`${this.serverURL}/authorization/token`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${btoa(username + ':' + password)}`
        }
    })
};


// VIEW LOGIC

const handleAuthenticationResponse = function(promise) {
    promise.then((response) => {
        let elementClasses = document.getElementById('invalid-login').classList;
        if (!response.ok) {
            elementClasses.remove("hidden");
            console.log("Invalid username or password!");
            return;
        }
        if (!elementClasses.contains("hidden")) {
            elementClasses.add("hidden");
        }
        console.log("Successfully aquired token!");
    }, (response) => {
        console.log("Failed to communicate with the server!");
    })
}

document.addEventListener('DOMContentLoaded', (event) => {
    let formElement = document.getElementById('login-form');
    console.log(document.getElementById('login'))
    formElement.addEventListener('submit', (event) => {
        event.preventDefault();
        window.history.back();
    }, true);
    document.getElementById('login').addEventListener('click', (event) => {
        let data = new FormData(formElement);
        let username = data.get("username");
        let password = data.get("password");
        console.log(`Username: ${username}\nPassword:${password}`)

        let authenticationPromise = authentication.authenticate(username, password);
        handleAuthenticationResponse(authenticationPromise);
    });
})