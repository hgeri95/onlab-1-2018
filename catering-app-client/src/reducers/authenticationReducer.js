import {userConstants} from '../constants/user.constants';

const initialState = {
    authenticated: false,
    username: "",
    refreshToken: "",
    roles: []
}

export default function(state = initialState, action) {
    switch(action.type) {
        case userConstants.AUTHENTICATED:
            return {
                ...state,
                authenticated: true,
                username: action.payload.username,
                refreshToken: action.payload.refreshToken,
                roles: action.payload.roles    
            };
        case userConstants.UNAUTHENTICATED:
            return {
                ...state,
                 authenticated: false,
                 username: "",
                 refreshToken: "",
                 roles: []    
            };
        default:
            return state;
    }
}