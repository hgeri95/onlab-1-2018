import axios from 'axios';
import Constants from "../constants/Constants";
import localStorage from 'react-native-sync-localstorage';

export const baseUrl = 'http://10.0.2.2:8080/api/v1';

const instance = axios.create({
   baseURL: baseUrl,
   headers: {'Content-Type': 'application/json'}
});

instance.interceptors.request.use(
    config => {
        console.log("Request interceptor called");
        const token = localStorage.getItem(Constants.JWTTOKEN);
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token;
        } else {
            config.headers['authorization'] = '';
        }
        return config;
    },
    error => {
        console.log("Request interceptor error: " + error);
        Promise.reject(error);
    }
);

instance.interceptors.response.use( (response) => {
    console.log("Response interceptor called");
    return response;
},  error => {
    console.log("Response interceptor error: " + error);
    const originalRequest = error.config;
    if (error.response.status !== 403) {
        return Promise.reject(error);
    }
    if (originalRequest._retry === undefined) {
        originalRequest._retry = true;
        const username =  localStorage.getItem(Constants.USERNAME);
        const refreshToken = localStorage.getItem(Constants.REFRESHTOKEN);
        return axios.post(baseUrl + '/authenticate/refresh',
            {
                userName: username,
                refreshToken: refreshToken
            })
            .then(res => {
                localStorage.setItem(Constants.JWTTOKEN, res.data.accessToken);
                localStorage.setItem(Constants.REFRESHTOKEN, res.data.refreshToken);
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

export const unsetLocalStorage = () => {
    localStorage.removeItem(Constants.JWTTOKEN);
    localStorage.removeItem(Constants.REFRESHTOKEN);
    localStorage.removeItem(Constants.USERNAME);
    localStorage.removeItem(Constants.ROLES);
};
