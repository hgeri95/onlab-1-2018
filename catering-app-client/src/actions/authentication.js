import API from '../utils/api';
import {userConstants} from '../constants/user.constants';
import setAuthToken from '../utils/SetAuthToken';

export const loginAction = ({username, password}) => dispatch => {
    API.post('/authenticate/login',
        {"username": username, "password": password})
        .then(res => {
            const accessToken = res.data.accessToken;
            localStorage.setItem('jwtToken', accessToken);
            setAuthToken(true);

            const loggedInUser = res.data.user.username;
            const refreshToken = res.data.refreshToken;
            const roles = res.data.user.roles;
            dispatch({
                type: userConstants.AUTHENTICATED,
                payload: {username: loggedInUser, refreshToken: refreshToken, roles: roles}
            });
        })
        .catch(err => {
            dispatch({
                type: userConstants.GET_ERRORS,
                payload: 'Wrong username or password'
            });
        });
};

export const registerUserAction = (user, history) => dispatch => {
    API.post('/users/sign-up', {username: user.name, password: user.password, roles: [user.role]})
        .then(res => {
            history.push('/login');
        })
        .catch(err => {
            dispatch({
                type: userConstants.GET_ERRORS,
                payload: 'Failed to register user'
            })
        });
};

export const logoutAction = (history) => dispatch => {
    API.post('/authenticate/logout')
        .then(res => {
            localStorage.removeItem('jwtToken');
            setAuthToken(false);
            history.push('/login');
            dispatch({
                type: userConstants.UNAUTHENTICATED,
                payload: {}
            });
        })
        .catch(err => {
            dispatch({
                type: userConstants.GET_ERRORS,
                payload: 'Failed to logout'
            })
        });
};
