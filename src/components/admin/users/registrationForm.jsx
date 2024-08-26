import { useContext, useState } from "react";
import styles from "./registrationForm.module.css";
import Select from "react-select";
import AuthService from "../../../Auth/services/AuthService";

const newUserData = {
  login: "",
  name: "",
  password: "",
  role: "",
  balance: 0,
};

const optionsRole = [
  {
    value: "USER",
    label: "User",
  },
  {
    value: "MANAGER",
    label: "Manager",
  },
  {
    value: "ADMIN",
    label: "Admin",
  },
];

const RegistrationForm = ({ activeBlock, setActive }) => {
  const [newUser, setNewUser] = useState(newUserData);
  const [rolePicked, setRolePicked] = useState("");

  const getValue = () => {
    return rolePicked ? optionsRole.find((c) => c.value === rolePicked) : "";
  };

  const onChange = (newValue) => {
    setRolePicked(newValue.value);
    setNewUser((prev) => ({
      ...prev,
      role: newValue.value,
    }));
  };

  const handleRegistration = async (e) => {
    // e.preventDefault();
    const regUser = async () => {
      const reg = await AuthService.registration(
        newUser.login,
        newUser.password,
        newUser.role,
        newUser.balance,
        newUser.name
      );
    };
    regUser();
  };

  return (
    <div
      className={activeBlock ? styles.authBlockActive : styles.authBlock}
      onClick={() => setActive(false)}
    >
      <form
        className={activeBlock ? styles.authContentActive : styles.authContent}
        onClick={(e) => e.stopPropagation()}
      >
        <div>
          <p className={styles.hint}>Логин</p>
          <input
            onChange={(e) =>
              setNewUser((prev) => ({
                ...prev,
                login: e.target.value,
              }))
            }
            required
            value={newUser.login}
            type="text"
            placeholder="Логин"
          />
        </div>
        <div>
          <p className={styles.hint}>Пароль</p>
          <input
            onChange={(e) =>
              setNewUser((prev) => ({
                ...prev,
                password: e.target.value,
              }))
            }
            required
            value={newUser.password}
            type="password"
            placeholder="Пароль"
          />
        </div>
        <div>
          <p className={styles.hint}>Имя</p>
          <input
            onChange={(e) =>
              setNewUser((prev) => ({
                ...prev,
                name: e.target.value,
              }))
            }
            required
            value={newUser.name}
            type="text"
            placeholder="Имя"
          />
        </div>
        <p className={styles.hint} style={{ marginBottom: "0px" }}>
          Роль
        </p>
        <Select
          options={optionsRole}
          value={getValue()}
          onChange={onChange}
          required
          onBlur={(e) =>
            setNewUser((prev) => ({
              ...prev,
              role: rolePicked,
            }))
          }
          placeholder="Роль"
        />
        <div>
          <p className={styles.hint}>Баланс</p>
          <input
            onChange={(e) =>
              setNewUser((prev) => ({
                ...prev,
                balance: +e.target.value,
              }))
            }
            required
            value={newUser.balance}
            type="number"
            placeholder="Баланс"
          />
        </div>
        <button
          className={styles.btn}
          onClick={(e) => {
            e.preventDefault()
            handleRegistration(e)
          }}
        >
          Зарегистрировать
        </button>
      </form>
    </div>
  );
};

export default RegistrationForm;
