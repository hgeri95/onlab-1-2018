import {userConstants} from "../constants/actionTypes";


const initialState = {
    authenticated: false,
    username: "",
    refreshToken: "",
    role: ""
};

export default function (state = initialState, action) {
    switch (action.type) {
        case userConstants.AUTHENTICATED:
            return {
                ...state,
                authenticated: true,
                username: action.payload.username,
                refreshToken: action.payload.refreshToken,
                role: action.payload.role
            };
        case userConstants.UNAUTHENTICATED:
            return {
                ...state,
                authenticated: false,
                username: "",
                refreshToken: "",
                role: ""
            };
        case userConstants.DELETE_USER:
            return {
                ...state,
                authenticated: false,
                username: "",
                refreshToken: "",
                role: ""
            };
        default:
            return state;
    }
}