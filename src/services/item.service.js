import api from "../Auth/http";

export default class ItemService {
  static async addItem(type, productName, description, price, sizes, image) {
    return api.post("/admin/item", {type, productName, description, price, sizes, image});
  }

  static async getAll() {
    const response = await api.get("/item");
    return response.data;
  }

  static async getById(itemId) {
    const response = await api.get(`/item/${itemId}`);
    // Переписать потом это на /item?itemId=${itemId}
    return response.data;
  }
  static async deleteItem(data) {
    const response = await api.delete("/admin/item", (data = { data }));
    return response;
  }

  static async editItem(data) {
    const response = await api.patch("/admin/item", ( data ));
    return response.data;
  }

  static async addToCart(userId, items) {
    const response = await api.post(`/admin/cart/${userId}`, { userId, items });
    return response.data;
  }
  static async getCart(userId) {
    const response = await api.get(`/admin/cart/${userId}`, { userId });
    return response.data;
  }
  static async getWareHouse() {
    const response = await api.get(`/admin/wh`);
    return response.data;
  }
  static async deleteItemCart(userId, itemId, sizeId) {
    const response = await api.delete(`/admin/cart/${userId}/${itemId}/${sizeId}`);
    return response.data;
  }

  static async order(
    userId,
    firstName,
    secondName,
    lastName,
    address,
    items,
    statusId
  ) {
    const response = await api.post(`/admin/order/${userId}`, {
      userId,
      firstName,
      secondName,
      lastName,
      address,
      items,
      statusId,
    });


    return response.data;
  }
}
