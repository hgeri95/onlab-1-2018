import API from '../utils/api';
import {userConstants} from '../constants/user.constants';
import setAuthToken from '../utils/setAuthToken';

export const loginAction = ({username, password}) => {
    return async (dispatch) => {
        try {
            const res = await API.post('/authenticate/login',
            {"username": username, "password": password});
            const accessToken = res.data.accessToken;
            localStorage.setItem('jwtToken', accessToken);
            setAuthToken(accessToken);
            dispatch({type: userConstants.AUTHENTICATED})

            //TODO set user details
        } catch(error) {
            dispatch({
                type: userConstants.AUTHENTICATION_ERROR,
                payload: 'Wrong username or password'
            })
        }
    };
}

export const registerUserAction = (user, history) => {
    return async (dispatch) => {

    }
}
