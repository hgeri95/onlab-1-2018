import API from './api';

export default function setAuthToken(token) {
    if (token) {
        API.defaults.headers.common['Authorization'] = 'Bearer ${token}';
    } else {
        delete API.defaults.headers.common['Authorization'];
    }
}