import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/auth', // Replace with your backend URL
});

export default api;
