client.user.authorize();

const handleAuthentication = function(promise) {
    resetAuthenticationNotification();
    promise.then(() => {
        const notificationElement = document.getElementById('login-notification')
        if (notificationElement != null) {
            notificationElement.remove();
        }
        client.user.authorize();
    }).catch(() => {
        displayAuthenticationFailureNotification(client.configuration.locale.user.credentials.invalid);
    });
}

const handleRegistration = function(promise) {
    resetAuthenticationNotification();
    promise.then((credentials) => {
        const notificationElement = document.getElementById('register-notification')
        if (notificationElement != null) {
            notificationElement.remove();
        }

        client.user.authenticate(credentials)
        .then(() => client.user.authorize())
        .catch(() => console.error(client.configuration.locale.user.credentials.invalid));
    }).catch((response) => {
        let messages = [];
        for (error of response.errors) {
            const locale = client.getLocale(error.key)
            if (locale == null) {
                continue;
            }
            messages.push(locale);
        }
        // not really ideal
        const message = messages.join('<br><br>');
        displayAuthenticationFailureNotification(message);
    });
}


const resetAuthenticationNotification = function() {
    const promptElement = document.getElementById('form-selected');
    const notification = promptElement.querySelector('.authentication-notification');
    notification.classList.add('invisible');
}

const displayAuthenticationFailureNotification = function(text) {
    const promptElement = document.getElementById('form-selected');
    const notification = promptElement.querySelector('.authentication-notification');
    const notificationText = notification.querySelector('.authentication-notification-text');
    notificationText.innerHTML = text;
    notification.classList.remove('invisible');


}

document.addEventListener('DOMContentLoaded', () => {
    ui.quote.display();

    const loginFormButton = document.getElementById('login-form-button');
    const registerForm = loginFormButton.parentElement;


    const registerFormButton = document.getElementById('register-form-button');
    const loginForm = registerFormButton.parentElement;


    registerFormButton.addEventListener('click', () => {
        loginForm.classList.add('hidden');
        loginForm.id = '';
        registerForm.id = 'form-selected';
		registerForm.classList.remove('hidden');
        
    })

    loginFormButton.addEventListener('click', () => {
        registerForm.classList.add('hidden');
        registerForm.id = '';
        loginForm.id = 'form-selected';
        loginForm.classList.remove('hidden');
    })

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        //window.history.back();
        
        const data = new FormData(loginForm);

        const promise = client.user.authenticate({
            username: data.get("username"),
            password: data.get("password")
        });
        handleAuthentication(promise);
    });

    registerForm.addEventListener('submit', (event) => {
        event.preventDefault();
        //window.history.back();
        
        const data = new FormData(registerForm);

        const promise = client.user.create({
            username: data.get("username"),
            password: data.get("password")
        });
        handleRegistration(promise);
    });
});