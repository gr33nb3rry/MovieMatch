const client = {}

client.configuration = {
    authorization: {
        keyName: 'mwid'
    },

    path: {
        server: {
            url: 'http://localhost:8080'
        },

        authentication: '/authentication/',
        home: '/dashboard/',
        session: '/session'
    },

    locale: {
        fetch: {
            connection: {
                success: 'Successfully connected to the server',
                failed: 'Failed to connect to the server'
            },
            failed: 'An error has occurred while retrieving data from the server'
        },
        user: {
            credentials: {
                invalid: 'Incorrect username or password',
                username: {
                    invalid: 'Username should consist of from 3 to 15 symbols. Allowed symbols: a-z A-Z 0-9 -_',
                    present: 'User with provided username already exists'
                },
                password: {
                    invalid: 'Password should consist of from 6 to 15 symbols. Allowed symbols: a-z A-Z 0-9 #?!@$ %^&*-_',

                }
            },
            present: 'User with the provided data already exists'
        }
    }
}

client.getLocale = function(key) {
    const sections = key.split('.');
    let node = client.configuration.locale;
    for (const section of sections) {
        if (!section) {
            return null;
        }
        if (!node.hasOwnProperty(section)) {
            return null;
        }
        node = node[section];
    }
    if (typeof node === 'string' || node instanceof String) {
        return node;
    }
    return null;
}

client.checkServerConnection = function(headers) {
    const statusPromise = fetch(`${client.configuration.path.server.url}/status`, {
        method: 'GET',
        headers: headers
    })
    
    statusPromise.then(() => console.log(client.configuration.locale.fetch.connection.success))
    .catch(() => console.error(client.configuration.locale.fetch.connection.failed));

    return statusPromise;
}

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

client.user = {};

/*
CAUTION: Don't use this without calling client.user.authorize() somewhere first.
This is a client-side fetching from JWT token, and server is needed to validate
the jwt token, before it could be used. This way we can retrieve some user
variables locally and server needs to validate it only once.
 */
client.user.getUsername = function() {
    const token = this.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    const payload = parseJwtPayload(token.value);
    return payload.sub;
}

/*
CAUTION: Don't use this without calling client.user.authorize() somewhere first.
This is a client-side fetching from JWT token, and server is needed to validate
the jwt token, before it could be used. This way we can retrieve some user
variables locally and server needs to validate it only once.
 */
client.user.getId = function() {
    const token = this.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    const payload = parseJwtPayload(token.value);
    return payload.sub_id;
}

client.user.getFriends = function() {
    const token = this.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    return new Promise((resolve, reject) => {
        let userId = client.user.getId();
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

                if (data[i].firstUserEntity.id === userId) {
                    friendId = data[i].secondUserEntity.id;
                    friendUsername = data[i].secondUserEntity.username;
                } else {
                    friendId = data[i].firstUserEntity.id;
                    friendUsername = data[i].firstUserEntity.username;
                }
                friendsList.push([friendId, friendUsername]);
            }
            resolve(friendsList);
        // TODO: CHECK IF WE shouldnt return this in case this fails. I dont like returning raw responses to the view specific javascript files.
        }).catch((response) => {
            console.error(client.configuration.locale.fetch.failed);
            reject(response)
        });
    });
}



client.user.create = function(credentials) {
    return new Promise((resolve, reject) => {
        fetch(`${client.configuration.path.server.url}/user/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        }).then(response => {
            if (response.ok) {
                resolve(credentials);
            } else if (response.status == 400) {
                response.json()
                    .then((jsonResponse) => reject(jsonResponse))
                    .catch(() => console.error(client.configuration.locale.fetch.failed))
            } else {
                console.error(client.configuration.locale.fetch.failed)
            }
        }).catch(() => console.error(client.configuration.locale.fetch.connection.failed));
    })
}

client.user.changePassword = function(password) {
    const token = this.getAuthorizationToken();
    if (token == null) {
        return null;
    }

    const credentials = {
        password: password
    }
    return new Promise((resolve, reject) => {
        fetch(`${client.configuration.path.server.url}/user/password`, {
            method: 'PATCH',
            headers: {
                'Authorization': `Bearer ${token.value}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(
                {
                    id: this.getId(),
                    credentials: credentials
                }
            )
        }).then((response) => {
            if (response.ok) {
                resolve(credentials);
            } else if (response.status == 400) {
                response.json()
                    .then((jsonResponse) => reject(jsonResponse))
                    .catch(() => console.error(client.configuration.locale.fetch.failed))
            } else {
                console.error(client.configuration.locale.fetch.failed)
            }
        }).catch(() => console.error(client.configuration.locale.fetch.connection.failed));
    })
}


client.user.authenticate = function(credentials) {
    const fetchPromise = fetch(`${client.configuration.path.server.url}/authorization/token`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${btoa(credentials.username + ':' + credentials.password)}`
        }
    })

    return new Promise((resolve, reject) => {
        fetchPromise.then((response) => {
            if (!response.ok) {
                if (response.status === 401) {
                    console.error(client.configuration.locale.user.credentials.invalid);
                }
                reject(response);
                return;
            }
            const textPromise = response.text();
            textPromise.then(text => {
                const token = btoa(text);
                const jsonPromise = JSON.parse(text);
                localStorage.setItem(client.configuration.authorization.keyName, token);
                resolve(jsonPromise);
            }).catch(() => {
                console.error(client.configuration.locale.fetch.failed);
            })
        }).catch(() => {
            console.error(client.configuration.locale.fetch.connection.failed);
        });
    });
};

client.user.isAuthorized = function() {
    return new Promise((resolve, reject) => {
        const token = this.getAuthorizationToken();
        if (token == null) {
            reject();
            return;
        }

        client.checkServerConnection({
            'Authorization': `Bearer ${token.value}`
        }).then((response) => {
            if (response.ok) {
                resolve();
                return;
            }
            if (response.status === 401) {
                localStorage.removeItem(client.configuration.authorization.keyName);
            }
            console.error(client.configuration.locale.fetch.failed);
            reject();
        }).catch(() => {
            console.error(client.configuration.locale.fetch.connection.failed);
            reject();
        })
    })
}

client.user.authorize = function() {
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

client.user.getAuthorizationToken = function() {
    const encodedToken = localStorage.getItem(client.configuration.authorization.keyName);
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
    localStorage.removeItem(client.configuration.authorization.keyName);
    return null;
}

// Not really an ideal solution, but we can't do this properly right now.
client.user.invalidateAuthorizationToken = function() {
    localStorage.removeItem(client.configuration.authorization.keyName);
}