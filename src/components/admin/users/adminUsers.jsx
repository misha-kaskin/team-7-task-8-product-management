import { useEffect, useState } from "react";
import UserService from "../../../Auth/services/UserService";
import styles from "./adminUsers.module.css";
import UserCard from "./userCard";
import AuthService from "../../../Auth/services/AuthService";

const AdminUsers = () => {
  const [usersData, setUsersData] = useState([]);

  const newUser = {
    login: 'test',
    name: 'test',
    password: 'test',
    role: 'ADMIN',
    balance: 9999
  }

  useEffect(() => {
    const fetchData = async () => {
      const data = await UserService.getUsers();
      setUsersData(data.data);
      
    };
    fetchData();
    const regUser = async () => {
      const reg = await UserService.registration(newUser)
    }
    regUser()
  }, []);

  return (
    <div className={styles.content}>
      <div className={styles.info}>
        <h1>Пользователи</h1>
      </div>
      <div className={styles.users}>
        {usersData
          ? usersData.map((user) => 
              <UserCard key={user.userId} user={user} />
            )
          : "Пользователи не найдены"}
      <div className={styles.addUser}>
        
      </div>
      </div>
    </div>
  );
};

export default AdminUsers;
