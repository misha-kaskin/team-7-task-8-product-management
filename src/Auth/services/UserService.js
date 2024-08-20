import api from "../http";

export default class UserSetvice {
  static async fetchUsers() {
    return api.get("/users");
  }
}
