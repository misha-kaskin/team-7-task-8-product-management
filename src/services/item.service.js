import api from "../Auth/http";

export default class ItemService {
  static async addItem(data) {
    return api.post("/admin/item", data);
  }

  static async getAll() {
    const response = await api.get("/admin/item");
    return response.data;
  }

  static async getById(itemId) {
    const response = await api.get(`/admin/item/${itemId}`);
    // Переписать потом это на /item?itemId=${itemId}
    console.log(response.data);
    
    return response.data;
  }
  static async deleteItem(data) {
    const response = await api.delete("/admin/item", data={data});
    return response.data;
  }

  static async editItem(data) {
    const response = await api.patch("/admin/item", data={data});
    return response.data;
  }
}