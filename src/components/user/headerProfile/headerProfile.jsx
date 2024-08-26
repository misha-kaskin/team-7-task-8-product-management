import { useContext, useEffect, useState } from "react";
import AuthService from "../../../Auth/services/AuthService";
import styles from "./headerProfile.module.css";
import { Context } from "../../../main";
import UserService from "../../../Auth/services/UserService";
import { Link } from "react-router-dom";

const HeaderProfile = ({ setAuthActive, setRole }) => {
  const [userData, setUserData] = useState({});
  const { store } = useContext(Context);

  useEffect(() => {
    const userId = JSON.parse(localStorage.getItem("user"))["userId"];
    const getUserData = async () => {
      const data = await UserService.getById(userId);
      setUserData(data.data);
    };
    getUserData();
  }, []);

  if (localStorage.getItem("user")) {
    const handleLogout = () => {
      store.logout();
      setAuthActive(false);
      setRole('')
    };

    return (
      <div className={styles.profileCard}>
        <div className={styles.profilePic}></div>
        <div className={styles.info}>
          <Link className={styles.name} to='/orders'>{userData.name}</Link>
          <div className={styles.userBalanceLogout}>
            <p className={styles.balance}>{`Баланс: ${
              userData.balance ? userData.balance : 0
            }`}</p>
            <p className={styles.logout} onClick={handleLogout}>
              Выход
            </p>
          </div>
        </div>
      </div>
    );
  }
};
export default HeaderProfile;
