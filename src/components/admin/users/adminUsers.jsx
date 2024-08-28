import { useEffect, useState } from "react";
import UserService from "../../../Auth/services/UserService";
import styles from "./adminUsers.module.css";
import UserCard from "./userCard";
import AuthService from "../../../Auth/services/AuthService";
import RegistrationForm from "./registrationForm";
import { set } from "react-hook-form";

const AdminUsers = () => {
  const [usersData, setUsersData] = useState([]);
  const [authActive, setAuthActive] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [status, setStatus] = useState(0);
  

  useEffect(() => {
    const fetchData = async () => {
      const response = await UserService.getUsers();
      response.data.sort((a ,b) => {
        return a.userId - b.userId
      })
      setUsersData(response.data);
      setFilteredUsers(response.data);
    };
    fetchData();
  }, [status]);

  useEffect(() => {
    if (options == 0) {
      setFilteredUsers(usersData);
    }
    if (options == 1) {
      const filter = [];
      usersData.map((user) => (user.role == "ADMIN" ? filter.push(user) : ""));
      setFilteredUsers(filter);
    }
    if (options == 2) {
      const filter = [];
      usersData.map((user) =>
        user.role == "MANAGER" ? filter.push(user) : ""
      );
      setFilteredUsers(filter);
    }
    if (options == 3) {
      const filter = [];
      usersData.map((user) => (user.role == "USER" ? filter.push(user) : ""));
      setFilteredUsers(filter);
    }
    console.log(filteredUsers);
  }, [options]);

  return (
    <div className={styles.content}>
      <div className={styles.info}>
        <h1>Пользователи</h1>
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
          Администраторы
        </button>
        <button
          className={options == 2 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(2);
          }}
        >
          Менеджеры
        </button>
        <button
          className={options == 3 ? styles.filterBtnActive : styles.filterBtn}
          style={{ marginRight: "20px" }}
          onClick={(e) => {
            setOptions(3);
          }}
        >
          Пользователи
        </button>
        <p className={styles.registration} onClick={() => setAuthActive(true)}>
          Регистрация
        </p>
      </div>
      <div className={styles.users}>
        {usersData
          ? filteredUsers
              .filter((user) => {
                return (searchTerm.toLowerCase === "" || !user.login || !user.name)
                  ? user
                  : user.login.toLowerCase().includes(searchTerm.toLowerCase()) || user.name.toLowerCase().includes(searchTerm.toLowerCase())
              })
              .map((user) => <UserCard key={user.userId} user={user} status={status} setStatus={setStatus}/>)
          : "Пользователи не найдены"}
      </div>
      <RegistrationForm activeBlock={authActive} setActive={setAuthActive} />
    </div>
  );
};

export default AdminUsers;
