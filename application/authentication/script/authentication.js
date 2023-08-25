client.user.identity.authorize();

const handleAuthentication = function(promise) {
    promise.then(response => {
        if (!response.ok) {
            displayAuthenticationFailureNotification(client.configuration.locale.invalidCredentials);
            return;
        }
        const notificationElement = document.getElementById('login-notification')
        if (notificationElement != null) {
            notificationElement.remove();
        }
        client.user.identity.authorize();
    }).catch(response => {
        displayAuthenticationFailureNotification(client.configuration.locale.connectionFailed);
    });
}

const displayAuthenticationFailureNotification = function(text) {
    const promptElement = document.getElementById('login-prompt');

    const firstPromptElement = promptElement.children[0];
    if (firstPromptElement.id === 'login-notification') {
        firstPromptElement.children[0].textContent = text;
        return;
    }
    const containerElement = document.createElement('div');
    const paragraphElement = document.createElement('p');
    const textNode = document.createTextNode(text);

    paragraphElement.classList.add("text-3");
    containerElement.classList.add("notification", "error-color");
    containerElement.id = 'login-notification';
    paragraphElement.appendChild(textNode);
    containerElement.appendChild(paragraphElement);
    promptElement.prepend(containerElement);
}

document.addEventListener('DOMContentLoaded', () => {
    let formElement = document.getElementById('login-form');

    formElement.addEventListener('submit', (event) => {
        event.preventDefault();
        //window.history.back();

        const data = new FormData(formElement);
        const username = data.get("username");
        const password = data.get("password");

        const promise = client.user.identity.authenticate(username, password);
        handleAuthentication(promise);
    }, true);
});