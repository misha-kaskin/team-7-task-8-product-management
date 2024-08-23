import { makeAutoObservable } from "mobx";
import AuthService from "../services/AuthService";
import axios from "axios";
import { API_URL } from "../http";

export default class Store {
  user = {};
  isAuth = false;

  constructor() {
    makeAutoObservable(this);
  }

  setAuth(bool) {
    this.isAuth = bool;
  }

  setUser(user) {
    this.user = user;
  }

  async login(login, password) {
    try {
      const response = await AuthService.login(login, password);
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("user", atob(response.data.token.split(".")[1]));
      this.setAuth(true);
      this.setUser(JSON.parse(atob(response.data.token.split(".")[1])));
    } catch (e) {
      console.log(e.response?.data?.message);
    }
  }

  async registration(login, password) {
    try {
      const response = await AuthService.registration(login, password);
    } catch (e) {
      console.log(e.response?.data?.message);
    }
  }

  async logout() {
    // try {
    //   const response = await AuthService.logout();
    localStorage.removeItem("token")
    localStorage.removeItem("user");
    this.setAuth(false);
    this.setUser({});
    // } catch (e) {
    //   console.log(e.response?.data?.message);
    // }
  }

  async CheckAuth() {
    this.setAuth(true);
    // this.setUser(response.data.user);
  }
}
