import { useEffect, useState } from "react";
import UserService from "../../Auth/services/UserService";
import styles from "./userOrder.module.css";
import OrderCard from "./orderCard";

const dataOrder = [
  {
    orderId: 1,
    userId: 3,
    status: {
      statusId: 1,
      title: "В работе",
    },
    orderDate: "2024-08-25",
    items: [
      {
        itemId: 70,
        productName: "Футболка",
        price: 1999,
        image: "",
        sizes: [
          {
            sizeId: 7,
            title: "nullSize",
            count: 1,
          },
        ],
      },
    ],
  },
];

const UserOrder = () => {
  const [ordersData, setOrdersData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filteredOrders, setFilteredOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const userId = JSON.parse(localStorage.getItem("user"))["userId"];
      const response = await UserService.getByOrderId(userId);

      response.data.sort((a, b) => {
        return b.orderId - a.orderId;
      });
      setOrdersData(response.data);
      setFilteredOrders(response.data);
    };
    fetchData();
    setOrdersData(dataOrder);
  }, []);

  useEffect(() => {
    if (options == 0) {
      setFilteredOrders(ordersData);
    }
    if (options == 1) {
      const filter = [];
      ordersData.map((order) =>
        order.status.title == "В работе" ? filter.push(order) : ""
      );
      setFilteredOrders(filter);
    }
    if (options == 2) {
      const filter = [];
      ordersData.map((order) =>
        order.status.title == "Собран" ? filter.push(order) : ""
      );
      setFilteredOrders(filter);
    }
    if (options == 3) {
      const filter = [];
      ordersData.map((order) =>
        order.status.title == "Выдан" ? filter.push(order) : ""
      );
      setFilteredOrders(filter);
    }
    if (options == 4) {
      const filter = [];
      ordersData.map((order) =>
        order.status.title == "Отменен" ? filter.push(order) : ""
      );
      setFilteredOrders(filter);
    }
  }, [options]);

  return (
    <div className={styles.content}>
      <div className={styles.info}>
        <h1>Мои заказы</h1>
        <input
          type="text"
          className={styles.search}
          value={searchTerm}
          placeholder="Поиск"
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button
          className={options == 0 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(0);
          }}
        >
          Все
        </button>
        <button
          className={options == 1 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(1);
          }}
        >
          В работе
        </button>
        <button
          className={options == 2 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(2);
          }}
        >
          Собраны
        </button>
        <button
          className={options == 3 ? styles.filterBtnActive : styles.filterBtn}
          style={{ marginRight: "20px" }}
          onClick={(e) => {
            setOptions(3);
          }}
        >
          Выданы
        </button>
        <button
          className={options == 4 ? styles.filterBtnActive : styles.filterBtn}
          style={{ marginRight: "20px" }}
          onClick={(e) => {
            setOptions(4);
          }}
        >
          Отклонены
        </button>
      </div>
      <div className={styles.orders}>
        {ordersData
          ? filteredOrders.map((order) => (
              <OrderCard key={order.orderId} order={order} />
            ))
          : "Нет заказов"}
      </div>
    </div>
  );
};

export default UserOrder;
