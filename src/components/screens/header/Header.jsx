import { Link } from "react-router-dom";
import styles from "./Header.module.css";
import LoginForm from "../../../Auth/components/LoginForm";
import { useContext, useEffect, useState } from "react";
import { Context } from "../../../main";
import { observer } from "mobx-react-lite";
import UserService from "../../../Auth/services/UserService";
import HeaderProfile from "../../user/headerProfile/headerProfile";

function Header() {
  const [authActive, setAuthActive] = useState(false);
  const { store } = useContext(Context);

  useEffect(() => {
    if (localStorage.getItem("token")) {
      store.CheckAuth();
    }
  }, []);

  const handleUploadUsers = (e) => {
    const fetchData = async () => {
      const data = await UserService.getUsers();
    };
    fetchData();
  };

  return (
    <>
      <div className={styles.headerbar}>
        <div className={styles.content}>
          <div className={styles.info}>
            <div className={styles.icon} />
            <Link to="/" className={styles.homeLink}>
              Товары
            </Link>
          </div>
          <Link to='/admin/users' onClick={(e) => handleUploadUsers(e)}>Пользователи</Link>
          <div className={styles.info}>
            {store.isAuth ? (
              <HeaderProfile setAuthActive={setAuthActive} />
            ) : (
              <button
                className={styles.btn}
                onClick={() => setAuthActive(true)}
              >
                Вход
              </button>
            )}
            <Link to="/cart" className={styles.cartIcon}></Link>
          </div>
        </div>
      </div>
      <LoginForm activeBlock={authActive} setActive={setAuthActive} />
    </>
  );
}

export default observer(Header);
