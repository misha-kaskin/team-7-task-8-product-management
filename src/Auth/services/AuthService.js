import api from "../http";

export default class AuthService {
  static async login(login, password) {
    return api.post("/login", { login, password });
  }

  static async registration(login, password, role, balance, name) {
    return api.post("/admin/registration", { login, password, role, balance, name });
  }

  // static async registration(login, password) {
  //   return api.post("/admin/registration", { login, password });
  // }

  static async logout() {
    return api.post("/logout");
  }
}
