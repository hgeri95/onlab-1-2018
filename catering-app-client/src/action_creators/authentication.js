import API from '../utils/api';
import {authConstants, userConstants} from "../constants/actionTypes";


//Actions
export const userAuthenticated = (loggedInUser, refreshToken, role) => ({
    type: userConstants.AUTHENTICATED,
    payload: {username: loggedInUser, refreshToken: refreshToken, role: role}
});

export const userLoggedOut = () => ({type: userConstants.UNAUTHENTICATED, payload: {}});

export const errorAction = (message) => ({type: userConstants.GET_ERRORS, payload: {message: message}});

export const noErrorAction = () => ({type: userConstants.NO_ERRORS, payload: {}});

//Action creators
export const loginAction = ({username, password}) => dispatch => {
    API.post('/authenticate/login', {"username": username, "password": password})
        .then(res => {
            console.log(res);
            const loggedInUser = res.data.username;
            const refreshToken = res.data.refreshToken;
            const role = res.data.role;
            setLocalStorage(res.data.accessToken, refreshToken, loggedInUser, role);
            //setAuthToken(true);
            dispatch(userAuthenticated(loggedInUser, refreshToken, role));
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction('Wrong username or password!'));
        });
};

export const registerUserAction = (user, history) => dispatch => {
    API.post('/users/sign-up', {username: user.name, password: user.password, roles: [user.role]})
        .then(res => {
            console.log(res);
            history.push('/login');
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to register user! Message: " + err.response.data.message));
        });
};

export const logoutAction = (history) => dispatch => {
    API.post('/authenticate/logout')
        .then(res => {
            console.log(res);
            unsetLocalStorage();
            //setAuthToken(false);
            history.push('/login');
            dispatch(userLoggedOut());
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction('Failed to logout'));
        });
};

function setLocalStorage(accessToken, refreshToken, loggedInUser, role) {
    localStorage.setItem(authConstants.JWTTOKEN, accessToken);
    localStorage.setItem(authConstants.REFRESHTOKEN, refreshToken);
    localStorage.setItem(authConstants.USERNAME, loggedInUser);
    localStorage.setItem(authConstants.ROLES, role);
}

function unsetLocalStorage() {
    localStorage.removeItem(authConstants.JWTTOKEN);
    localStorage.removeItem(authConstants.REFRESHTOKEN);
    localStorage.removeItem(authConstants.USERNAME);
    localStorage.removeItem(authConstants.ROLES);
}