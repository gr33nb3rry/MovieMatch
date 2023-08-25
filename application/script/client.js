const client = {}

client.configuration = {
    identityKey: 'mwid',

    path: {
        server: {
            url: 'http://localhost:8080'
        },

        authentication: '/authentication/',
        home: '/dashboard/',
        session: '/session'
    },

    locale: {
        connectionFailed: 'Failed to connect to the server. Please wait a couple of minutes and try again!',
        invalidInboundData: 'An error has occurred while retrieving data from the server. Please contact administrator of the application to resolve this issue!',
        connectionSuccess: 'Successfully connected to the server!',
        invalidCredentials: 'Incorrect username or password. Please check credentials and try again!'
    }
}

client.user = {};

client.getMovieQuote = function() {
    return fetch(`${client.configuration.path.server.url}/quote`, {
        method: 'GET'
    }).then((response) => response.json());
}

// credit: https://stackoverflow.com/questions/38552003/how-to-decode-jwt-token-in-javascript-without-using-a-library
function parseJwtPayload (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}
/*
CAUTION: Don't use this without calling client.user.identity.authorize() somewhere first.
This is a client-side fetching from JWT token, and server is needed to validate
the jwt token, before it could be used. This way we can retrieve some user
variables locally and server needs to validate it only once.
 */
client.user.getUsername = function() {
    const token = this.identity.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    const payload = parseJwtPayload(token.value);
    return payload.sub;
}

/*
CAUTION: Don't use this without calling client.user.identity.authorize() somewhere first.
This method uses client side fetching of username, which could be spoofed if the fetching was
not validated by the server at all.

This could be completely client-sided in the future, since we can store user id's in JWT tokens
but in order for this to be implemented, we need to implement User DTO classes, which I don't have
time to implement for now.

Even if we decide to make this server sided, backend API is really not great since it doesn't follow json format
consistency since it returns raw numbers instead of json formatted documents.
 */
client.user.getId = function() {
    const username = this.getUsername();
    if (username == null) {
        return null;
    }

    const token = this.identity.getAuthorizationToken();
    if (token == null) {
        return null;
    }
    return new Promise((resolve, reject) => {
        fetch(`${client.configuration.path.server.url}/user/getLoginID?username=${username}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token.value}`
            },
        }).then(response => response.text())
        .then(data => {
            const id = Number(data);
            resolve(id);
        }).catch((response) => {
            console.log(client.configuration.locale.invalidInboundData);
            reject(response)
        });
    })
}

client.user.getFriends = function() {
    const token = this.identity.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    return new Promise((resolve, reject) => {
        let userIdPromise = client.user.getId();

        userIdPromise.then((userId) => {
            fetch(`${client.configuration.path.server.url}/friendship/byID?id=${userId}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token.value}`
                },
            }).then(response => response.json())
            .then(data => {
                let friendsList = [];

                // Why would we ever want to deserialize on the client, leave that job for server
                // with dto objects! We need some redesign to backend API!
                for (let i = 0; i < data.length; i++) {
                    let friendId;
                    let friendUsername;

                    if (data[i].user1ID.userID === userId) {
                        friendId = data[i].user2ID.userID;
                        friendUsername = data[i].user2ID.userName;
                    } else {
                        friendId = data[i].user1ID.userID;
                        friendUsername = data[i].user1ID.userName;
                    }
                    friendsList.push([friendId, friendUsername]);
                }
                resolve(friendsList);
            }).catch((response) => {
                console.log(client.configuration.locale.invalidInboundData);
                reject(response)
            });
        }).catch((response) => {
            console.log(client.configuration.locale.invalidInboundData);
            reject(response)
        });
    });
}

client.user.identity = {};

client.user.identity.authenticate = function(username, password) {
    const fetchPromise = fetch(`${client.configuration.path.server.url}/authorization/token`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${btoa(username + ':' + password)}`
        }
    })

    return new Promise((resolve, reject) => {
        fetchPromise.then((response) => {
            if (!response.ok) {
                if (response.status === 401) {
                    console.error(client.configuration.locale.invalidCredentials);
                }
                resolve(response);
                return;
            }
            console.log(client.configuration.locale.connectionSuccess);

            const textPromise = response.text();
            textPromise.then(text => {
                const token = btoa(text);
                localStorage.setItem(client.configuration.identityKey, token);
                resolve(response);
            }).catch(() => {
                console.error(client.configuration.locale.invalidInboundData);
                resolve(response);
            })
        }).catch((response) => {
            console.error(client.configuration.locale.connectionFailed);
            reject(response);
        });
    });
};

client.user.identity.isAuthorized = function() {
    const token = this.getAuthorizationToken();
    if (token == null) {
        return Promise.reject();
    }

    return new Promise((resolve, reject) => {
        fetch(`${client.configuration.path.server.url}/status`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token.value}`
            }
        }).then((response) => {
            if (response.ok) {
                resolve();
                return;
            }
            if (response.status === 401) {
                localStorage.removeItem(client.configuration.identityKey);
            }
            console.error(client.configuration.locale.invalidInboundData);
            reject();
        }).catch(() => {
            console.error(client.configuration.locale.connectionFailed);
            reject();
        })
    })
}

client.user.identity.authorize = function() {
    this.isAuthorized().then(() => {
        if (window.location.pathname === client.configuration.path.authentication) {
            window.location.replace(client.configuration.path.home);
        }
    }).catch(() => {
        if (window.location.pathname !== client.configuration.path.authentication) {
            window.location.replace(client.configuration.path.authentication);
        }
    })
}

client.user.identity.getAuthorizationToken = function() {
    const encodedToken = localStorage.getItem(client.configuration.identityKey);
    if (encodedToken == null) {
        return null;
    }
    const jsonToken = atob(encodedToken);

    const token = JSON.parse(jsonToken);

    const currentDate = Date.now();
    const tokenExpirationDate = Date.parse(token.expirationTime);
    if (currentDate < tokenExpirationDate) {
        return token;
    }
    localStorage.removeItem(client.configuration.identityKey);
    return null;
}