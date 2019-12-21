import React from 'react';
import API from '../utils/Api';
import Constants from "../constants/Constants";
import localStorage from 'react-native-sync-localstorage';

export const authenticate = (data, dispatch) => {
    console.log("Call authenticate");
    API.post('/authenticate/login',
        {"username": data.username, "password": data.password})
        .then(res => {
            console.log(res.data);
            setLocalStorage(res.data.accessToken, res.data.refreshToken, res.data.username, res.data.role);
            dispatch({type: Constants.LOGIN, payload: res.data});
        })
        .catch(err => {
            console.log(err.response);
            dispatch({type: Constants.ERROR, payload: "Wrong username or password!"});
        });
};

export const logout = (navigation, dispatch) => {
    console.log("Logout");
    API.post('/authenticate/logout')
        .then(res => {
            console.log(res.data);
            unsetLocalStorage()
            dispatch({type: Constants.LOGOUT, payload: res});
            navigation.navigate('Login');
        })
        .catch(err => {
            console.log(err.response);
            unsetLocalStorage()
            dispatch({type: Constants.LOGOUT, payload: err});
            navigation.navigate('Login');
        });
};

export const setLocalStorage = (accessToken, refreshToken, loggedInUser, role) => {
    localStorage.setItem(Constants.JWTTOKEN, accessToken);
    localStorage.setItem(Constants.REFRESHTOKEN, refreshToken);
    localStorage.setItem(Constants.USERNAME, loggedInUser);
    localStorage.setItem(Constants.ROLES, role);
};


const unsetLocalStorage = () => {
    localStorage.removeItem(Constants.JWTTOKEN);
    localStorage.removeItem(Constants.REFRESHTOKEN);
    localStorage.removeItem(Constants.USERNAME);
    localStorage.removeItem(Constants.ROLES);
};

