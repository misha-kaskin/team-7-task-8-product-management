import { useEffect, useState } from "react";
import UserService from "../../Auth/services/UserService";
import styles from "./userOrder.module.css";
import OrderCard from "./orderCard";
import AdminTableOrder from "./adminTableOrders";

const columns = [
  {
    title: "№ заказа",
    dataIndex: "orderId",
    key: "orderId",
  },
  {
    title: "Статус",
    dataIndex: "status.title",
    key: "status.title",
  },
  {
    title: "Дата",
    dataIndex: "orderDate",
    key: "orderDate",
  },
  {
    title: "Фамилия",
    dataIndex: "secondName",
    key: "secondName",
  },
  {
    title: "Имя",
    dataIndex: "firstName",
    key: "firstName",
  },
  {
    title: "Отчество",
    dataIndex: "middleName",
    key: "middleName",
  },
  {
    title: "Адрес",
    dataIndex: "address",
    key: "address",
  },
  {
    title: "Сумма заказа",
    dataIndex: "",
    key: "",
  },
];

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

const AdminOrder = () => {
  const [ordersData, setOrdersData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filteredOrders, setFilteredOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await UserService.getAllOrders();
      response.data.sort((a ,b) => {
        return  b.orderId - a.orderId  
      })
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
        <h1>Все заказы</h1>
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
      <table className={styles.tableHeader}>
          <thead >
            <tr>
              <th className={styles.headerBorder}>№ Заказа</th>
              <th className={styles.headerBorder}>Статус</th>
              <th className={styles.headerBorder}>Дата</th>
              <th className={styles.headerBorder}>Фамилия</th>
              <th className={styles.headerBorder}>Имя</th>
              <th className={styles.headerBorder}>Отчество</th>
              <th className={styles.headerBorder}>Адрес</th>
              <th className={styles.headerBorder}>Сумма заказа</th>
            </tr>
          </thead>
          <tbody>
        {ordersData
          ? filteredOrders
              // .filter((user) => {
              //   return searchTerm.toLowerCase === "" ||
              //     !user.login ||
              //     !user.name
              //     ? user
              //     : user.login
              //         .toLowerCase()
              //         .includes(searchTerm.toLowerCase()) ||
              //         user.name
              //           .toLowerCase()
              //           .includes(searchTerm.toLowerCase());
              // })
              .map((order) => (
                <AdminTableOrder
                  key={order.orderId}
                  order={order}
                  status={status}
                />
              ))
          : "Пользователи не найдены"}

          </tbody>
        </table>
        {/* {ordersData
          ? filteredOrders.map((order) => (
              <OrderCard key={order.orderId} order={order} />
            ))
          : "Нет заказов"} */}
      </div>
    </div>
  );
};

export default AdminOrder;
