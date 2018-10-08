import {userConstants} from '../constants/user.constants';

const initialState = {
    authenticated: false
}

export default function(state = initialState, action) {
    switch(action.type) {
        case userConstants.AUTHENTICATED:
            return {...state, authenticated: true};
        case userConstants.UNAUTHENTICATED:
            return {...state, authenticated: false};
        default:
            return state;
    }
}