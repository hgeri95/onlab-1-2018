import API from '../utils/api';
import {userConstants} from '../constants/user.constants';

export const getUserInfoAction = () => dispatch => {
    API.get('/users/userinfo')
        .then(res => {
                dispatch({
                    type: userConstants.GET_USER_INFO,
                    payload: res.data
                });
            }
        )
        .catch(err => {
                dispatch({
                    type: userConstants.GET_ERRORS,
                    payload: err.response.data
                });
            }
        );
};

export const postUserInfoAction = (userInfo, history) => dispatch => {
    API.post('users/userinfo', userInfo)
        .then(res => {
                dispatch({
                    type: userConstants.REFRESH_USER_INFO,
                    payload: res.data
                });
            }
        )
        .catch(err => {
                dispatch({
                    type: userConstants.GET_ERRORS,
                    payload: 'Failed to change user info'
                });
            }
        )
};