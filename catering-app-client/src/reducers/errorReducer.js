import { userConstants } from '../constants/user.constants';

const initialState = {};

export default function(state = initialState, action) {
    switch(action.type) {
        case userConstants.GET_ERRORS:
            return action.payload;
        default:
            return state;
    }
}