import { useContext } from "react";
import AuthService from "../../../Auth/services/AuthService";
import styles from "./headerProfile.module.css";
import { Context } from "../../../main";

const HeaderProfile = ({ setAuthActive }) => {
  const { store } = useContext(Context);
  if (localStorage.getItem("user")) {
    const userData = JSON.parse(localStorage.getItem("user"));

    const handleLogout = () => {
      store.logout();
      setAuthActive(false);
    };
    
    return (
      <div className={styles.profileCard}>
        <div className={styles.profilePic}></div>
        <div className={styles.info}>
          <div>{userData.name}</div>
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
