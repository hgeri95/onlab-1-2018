//reducers/index.js
import { combineReducers } from 'redux';
import authenticationReducer from './authenticationReducer';
import errorReducer from './errorReducer';

export default combineReducers({
    errors: errorReducer,
    authentication: authenticationReducer
});