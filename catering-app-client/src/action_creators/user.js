import API from '../utils/api';
import {userConstants} from "../constants/actionTypes";

//Actions
export const errorAction = (message) => ({type: userConstants.GET_ERRORS, payload: {message: message}});

export const noErrorAction = () => ({type: userConstants.NO_ERRORS, payload: {}});

export const getUserInfoActionObj = (data) => ({type: userConstants.GET_USER_INFO, payload: data});

export const refreshUserInfoAction = (data) => ({type: userConstants.REFRESH_USER_INFO, payload: data});

export const deleteUserAction = () => ({type: userConstants.DELETE_USER, payload: {}});

//Action creators
export const getUserInfoAction = () => dispatch => {
    API.get('/users/userinfo')
        .then(res => {
                console.log(res);
                dispatch(getUserInfoActionObj(res.data));
                dispatch(noErrorAction());
            }
        )
        .catch(err => {
                console.log(err);
                dispatch(errorAction("Failed to get user info! Message: " + err.response.data.message));
            }
        );
};

export const putUserInfoAction = (userInfo, history) => dispatch => {
    API.put('users/userinfo', userInfo)
        .then(res => {
                console.log(res);
                dispatch(refreshUserInfoAction(res.data));
                dispatch(noErrorAction());
            }
        )
        .catch(err => {
                console.log(err);
                dispatch(errorAction("Failed to change user info! Message: " + err.response.data.message));
            }
        )
};

export const deleteUser = (history) => dispatch => {
    API.delete('users')
        .then(res => {
            console.log(res);
            history.push('/login');
            dispatch(deleteUserAction());
            dispatch(noErrorAction());
        })
        .catch(err => {
            console.log(err);
            dispatch(errorAction("Failed to delete user! Message: " + err.response.data.message));
        })
};
