import { Link } from "react-router-dom";
import styles from "./Header.module.css";
import LoginForm from "../../../Auth/components/LoginForm";
import { useContext, useEffect, useState } from "react";
import { Context } from "../../../main";

function Header() {
  const [authActive, setAuthActive] = useState(false);
  const [logged, setLogged] = useState(false);
  const { store } = useContext(Context);

  useEffect(() => {
    if (localStorage.getItem('token')) {
      store.CheckAuth()
    }
  }, []);

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
          <div className={styles.info}>
            {store.isAuth ? (
              "Вы вошли"
            ) : (
              <button
                className={styles.btn}
                onClick={() => setAuthActive(true)}
              >
                Вход
              </button>
            )}

            <div className={styles.cartIcon}>
              <a />
            </div>
          </div>
        </div>
      </div>
      <LoginForm
        activeBlock={authActive}
        setActive={setAuthActive}
        logged={logged}
        setLogged={setLogged}
      />
    </>
  );
}

export default Header;
