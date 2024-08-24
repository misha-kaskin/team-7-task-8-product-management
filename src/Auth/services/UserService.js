import api from "../http";

export default class UserService {
  static async getUsers() {
    return api.get("/admin/users");
  }
  static async registration(data) {
    return api.post("/admin/registration", {data});
  }


}
