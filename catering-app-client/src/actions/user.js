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
}