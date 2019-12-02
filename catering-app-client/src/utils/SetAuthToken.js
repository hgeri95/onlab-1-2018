import API from './api';
import {authConstants} from "../constants/actionTypes";

/*
export default function setAuthToken(token) {
    if (token) {
        API.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(authConstants.JWTTOKEN);
    } else {
        delete API.defaults.headers.common['Authorization'];
    }
}*/