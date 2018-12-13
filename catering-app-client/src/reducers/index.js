//reducers/index.js
import { combineReducers } from 'redux';
import authenticationReducer from './authenticationReducer';
import errorReducer from './errorReducer';
import userReducer from './userReducer';
import cateringReducer from "./cateringReducer";

export default combineReducers({
    errors: errorReducer,
    authentication: authenticationReducer,
    user: userReducer,
    catering: cateringReducer
});