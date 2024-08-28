import api from "../http";

export default class UserService {
  static async getUsers() {
    return api.get("/admin/users");
  }
  static async registration(data) {
    return api.post("/auth", {data});
  }

  static async edit(userId, name, balance, role) {
    return api.patch("/admin/user-edit", {userId, name, balance, role});
  }

  static async delete(userId) {
    return api.delete(`/admin/user-remove/${userId}`);
  }

  static async getById(userId) {
    return api.get(`/admin/users/${userId}`);
  }
  
  static async getByOrderId(userId) {
    return api.get(`/admin/order/${userId}`);
  }
  
  static async getAllOrders() {
    return api.get(`/admin/order`);
  }
}
