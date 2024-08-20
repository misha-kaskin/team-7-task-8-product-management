import { Link } from "react-router-dom";
import styles from "./Header.module.css";
import Auth from "../auth/auth";
import { useState } from "react";

function Header() {
  const [authActive, setAuthActive] = useState(false);
  const [logged, setLogged] = useState(false);

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
            {logged ? (
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
      <Auth
        activeBlock={authActive}
        setActive={setAuthActive}
        logged={logged}
        setLogged={setLogged}
      />
    </>
  );
}

export default Header;
