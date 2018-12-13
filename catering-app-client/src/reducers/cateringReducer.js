import {cateringConstants} from "../constants/catering.constants";

const initialState = {
  cateringUnits: ""
};

export default function (state = initialState, action) {
    switch(action.type) {
        case cateringConstants.GET_ALL_CATERINGS:
            return {
                ...state,
                cateringUnits: action.payload
            };
        default:
            return state;
    }
}