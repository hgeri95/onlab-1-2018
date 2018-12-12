import axios from 'axios';

export default axios.create({
    baseURL: 'http://35.204.158.231:8080',
    headers: {'Content-Type': 'application/json'}
});