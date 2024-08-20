import axios from 'axios'

const url = 'http://localhost:3000/items'

export const ItemService = {
  async getAll() {
    const response = await axios.get(`${url}`)
    return response.data
  },

  async getById(id) {
    const response = await axios.get(`${url}?id=${id}`)
    return response.data[0]
  }
}