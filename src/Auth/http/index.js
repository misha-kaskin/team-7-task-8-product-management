import axios from "axios";

export const API_URL = "http://89.191.225.168:7070/v1/api";

const api = axios.create({
  withCredentials: false,
  baseURL: API_URL,
  headers: {
    token: localStorage.getItem("token")}
});

// api.interceptors.request.use((config) => {
//   config.headers.Token = localStorage.getItem("token");
//   return config;
// });

export default api;
