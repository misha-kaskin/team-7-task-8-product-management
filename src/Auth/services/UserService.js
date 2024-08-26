import api from "../http";

export default class UserService {
  static async getUsers() {
    return api.get("/admin/users");
  }
  static async registration(data) {
    return api.post("/auth", {data});
  }

  static async edit(data) {
    return api.patch("/admin/edit-user", {data});
  }

  static async delete(data) {
    return api.patch("/admin/delete-user", {data});
  }


}
