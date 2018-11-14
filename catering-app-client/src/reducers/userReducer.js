import {userConstants} from '../constants/user.constants';

const initialState = {
    userInfo: ""
}

export default function (state = initialState, action) {
    switch (action.type) {
        case userConstants.GET_USER_INFO:
            return {
                ...state,
                userInfo: action.payload
            };
        case userConstants.REFRESH_USER_INFO:
            return {
                ...state,
                userInfo: action.payload
            };
        default:
            return state;
    }
}