import { useState } from "react";
import styles from "./adminUsers.module.css";
import Select from "react-select";

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

const customStyles = {
  control: (provided, state) => ({
    ...provided,
    background: "#fff",
    borderColor: "#9e9e9e",
    minHeight: "20px",
    height: "20px",
    fontSize: "10px",
    padding: '0px',
    display : 'flex',
    textAlign: 'center',
  }),

  valueContainer: (provided, state) => ({
    ...provided,
    height: "20px",
    padding: "0px 0px 0px 2px",
    fontSize: "10px",
  }),

  input: (provided, state) => ({
    ...provided,
    margin: "0px",
    padding: '0px',
    fontSize: "10px",
    height: '10px !important'
  }),
  indicatorSeparator: (state) => ({
    display: "none",
    fontSize: "10px",
  }),
  indicatorsContainer: (provided, state) => ({
    ...provided,
    height: "20px",
    fontSize: "10px",
  }),
  container : (provided, state) => ({
    ...provided,
    height: "10px",
    fontSize: "10px",
  }),
  indicatorsContainer : (provided, state) => ({
    ...provided,
    height: "20px",
  }),
};

const noUserData = {
  name: '',
  role: '',
  balance: '',
}

const UserCard = ({ user }) => {
  const [edit, setEdit] = useState(false);
  const [userData, setUserData] = useState(noUserData)
  const [role, setRole] = useState(user.role);

  const handleEdit = (e) => {
    e.preventDefault();
    setEdit(true);
  };

  const getValue = () => {
    return role ? optionsRole.find((c) => c.value === role) : "";
  };

  const onChange = (newValue) => {
    setRole(newValue);
  };

  return (
    <div className={styles.userCard}>
      <div className={styles.userInfoWrapper}>
        <div className={styles.userAvatar}></div>
        <div className={styles.userInfo}>
          <div className={styles.idWrapper}>
            <p className={styles.text}>{`id: ${user.userId}`}</p>
            <p className={styles.text}>{`Логин: ${user.login}`}</p>
          </div>
          <div className={styles.nameWrapper}>
            <p className={styles.text}>{`Имя: ${edit ? "" : user.name}`}</p>
            {edit ? <input type="text" className={styles.inputUserData}/> : ''}
          </div>
        </div>
      </div>
      <div className={styles.roleWrapper}>
        <p className={styles.text}>{`Роль: ${edit ? "" : user.role}`}</p>
        {edit ? (
          <Select
            options={optionsRole}
            value={getValue()}
            onChange={onChange}
            required
            onBlur={(e) =>
              setItemData((prev) => ({
                ...prev,
                type: values.currentValueType,
              }))
            }
            placeholder="Роль"
            styles={customStyles}
          />
        ) : (
          ""
        )}
        <p className={styles.text}>{`Баланс: ${user.balance}`}</p>
        <div
          className={edit ? styles.accept : styles.edit}
          onClick={(e) => handleEdit(e)}
        ></div>
      </div>
    </div>
  );
};

export default UserCard;
