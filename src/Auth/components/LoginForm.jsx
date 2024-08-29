import { useContext, useState } from "react";
import styles from "./LoginForm.module.css";
import { useForm } from "react-hook-form";
import { observer } from "mobx-react-lite";
import { Context } from "../../main";

const LoginForm = ({ activeBlock, setActive, setRole, reloadBool, setReloadBool }) => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const { store } = useContext(Context);

  return (
    <div
      className={
        !store.isAuth && activeBlock ? styles.authBlockActive : styles.authBlock
      }
      onClick={() => setActive(false)}
    >
      <form
        className={activeBlock ? styles.authContentActive : styles.authContent}
        onClick={(e) => e.stopPropagation()}
      >
        <div>
          <p className={styles.hint}>Логин</p>
          <input
            onChange={(e) => setLogin(e.target.value)}
            value={login}
            type="text"
            id="login"
            placeholder="Логин"
          />
        </div>
        <div>
          <p className={styles.hint}>Пароль</p>
          <input
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            type="password"
            id="password"
            placeholder="Пароль"
          />
        </div>
        <button
          className={styles.btn}
          onClick={(e) => {
            e.preventDefault();
            store.login(login, password);
            setReloadBool(!reloadBool)
          }}
        >
          Войти
        </button>
      </form>
    </div>
  );
};

export default observer(LoginForm);