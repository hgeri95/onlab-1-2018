import {userConstants} from "../constants/actionTypes";


const initialState = {
    userInfo: "",
    userDeleted: false
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
        case userConstants.DELETE_USER:
            return {
                ...state,
                userDeleted: true
            }
        default:
            return state;
    }
}