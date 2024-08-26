import { useEffect, useState } from "react";
import UserService from "../../Auth/services/UserService";
import styles from "./userOrder.module.css";

const UserOrder = () => {
  const [ordersData, setOrdersData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filteredOrders, setFilteredOrders] = useState([])

  useEffect(() => {
    const fetchData = async () => {
      const userId = JSON.parse(localStorage.getItem("user"))["userId"];
      const data = await UserService.getByOrderId(userId);
      setOrdersData(data.data);
      setFilteredOrders(data.data);
    };
    // fetchData();
  }, []);

  useEffect(() => {
    if (options == 0) {
    setFilteredOrders(ordersData)
    
    }
    if (options == 1) {
      const filter = []
      ordersData.map(user => 
        user.role == 'ADMIN' ? filter.push(user) : '')
    setFilteredOrders(filter)
    }
    if (options == 2) {
      const filter = []
      ordersData.map(user => 
        user.role == 'MANAGER' ? filter.push(user) : '')
    setFilteredOrders(filter)
    }
    if (options == 3) {
      const filter = []
      ordersData.map(user => 
        user.role == 'USER' ? filter.push(user) : '')
    setFilteredOrders(filter)
    }
    console.log(filteredOrders);
    
  }, [options])

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
        {/* {ordersData
          ? filteredOrders.map((user) => <UserCard key={user.userId} user={user} />)
          : "Пользователи не найдены"} */}
      </div>
    </div>
  );
}

export default UserOrder