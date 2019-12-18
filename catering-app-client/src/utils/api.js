import axios from 'axios';
import {authConstants} from "../constants/actionTypes";

export const baseUrl = 'http://localhost:8080';

const instance = axios.create({
    baseURL: baseUrl + '/api/v1',
    headers: {'Content-Type': 'application/json'}
});

instance.interceptors.request.use(
    config => {
        const token = localStorage.getItem(authConstants.JWTTOKEN);
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token;
        } else {
            config.headers['authorization'] = '';
        }
        return config;
    },
    error => {
        Promise.reject(error);
    }
);

instance.interceptors.response.use((response) => {
    return response;
}, error => {
    const originalRequest = error.config;
    if (error.response.status !== 403) {
        return Promise.reject(error);
    }
    if (originalRequest._retry === undefined) {
        originalRequest._retry = true;
        const username = localStorage.getItem(authConstants.USERNAME);
        const refreshToken = localStorage.getItem(authConstants.REFRESHTOKEN);
        return axios.post(baseUrl + '/api/v1/authenticate/refresh',
            {
                userName: username,
                refreshToken: refreshToken
            })
            .then(res => {
                localStorage.setItem(authConstants.JWTTOKEN, res.data.accessToken);
                localStorage.setItem(authConstants.REFRESHTOKEN, res.data.refreshToken);
                error.response.config.headers['Authorization'] = 'Bearer ' + res.data.accessToken;
                return axios(error.response.config);
            }).catch(error => {
                unsetLocalStorage();
                return Promise.reject(error);
            });

    }
    return Promise.reject(error);
});

export default instance;

function unsetLocalStorage() {
    localStorage.removeItem(authConstants.JWTTOKEN);
    localStorage.removeItem(authConstants.REFRESHTOKEN);
    localStorage.removeItem(authConstants.USERNAME);
    localStorage.removeItem(authConstants.ROLES);
}
