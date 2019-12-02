import {userConstants} from "../constants/actionTypes";

const initialState = {
    errors: {}
};

export default function (state = initialState, action) {
    switch (action.type) {
        case userConstants.GET_ERRORS:
            return {
                ...state,
                errors: action.payload
            };
        case userConstants.NO_ERRORS:
            return initialState;
        default:
            return state;
    }
}