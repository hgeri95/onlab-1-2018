import { userConstants } from '../constants/user.constants';

const initialState = {};

export default function(state = initialState, action) {
    switch(action.type) {
        case userConstants.AUTHENTICATION_ERROR:
            return action.payload;
        default:
            return state;
    }
}