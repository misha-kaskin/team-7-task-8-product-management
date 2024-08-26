import { useEffect, useState } from "react";
import styles from "./adminUsers.module.css";
import Select from "react-select";
import UserService from "../../../Auth/services/UserService";

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
    fontSize: "10px",
    textAlign: "center",
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
    minHeight: "20px",
    padding: "0px",
    textAlign: "center",
    fontSize: "10px",
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
  container: (provided, state) => ({
    ...provided,
    height: "10px",
    fontSize: "10px",
  }),
  indicatorsContainer: (provided, state) => ({
    ...provided,
    height: "20px",
  }),
};

const noUserData = {
  userId: "",
  name: "",
  role: "",
  balance: "",
};

const UserCard = ({ user }) => {
  const [edit, setEdit] = useState(false);
  const [userData, setUserData] = useState(noUserData);
  const [rolePicked, setRolePicked] = useState(user.role);

  useEffect(() => {
    setUserData(() => ({
      userId: user.userId,
      name: user.name,
      role: user.role,
      balance: +user.balance,
    }));
  }, []);

  const handleEdit = (e) => {
    e.preventDefault();
    const bool = !edit;
    setEdit(bool);
  };

  const handleAccept = async (e) => {
    e.preventDefault();
    const bool = !edit;
    setEdit(bool);
    console.log(userData);
    const fetchData = async () => {
      const response = await UserService.edit(userData);
      console.log(response);
    };

    fetchData();
  };

  const handleDelete = async () => {
    const deleteUser = await UserService.delete(userData)
    deleteUser()
  }

  const getValue = () => {
    return rolePicked ? optionsRole.find((c) => c.value === rolePicked) : "";
  };

  const onChange = (newValue) => {
    setRolePicked(newValue.value);
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
            {edit ? (
              <input
                type="text"
                className={styles.inputUserData}
                placeholder={user.name}
                value={userData.name}
                onChange={(e) =>
                  setUserData((prev) => ({
                    ...prev,
                    name: e.target.value,
                  }))
                }
              />
            ) : (
              ""
            )}
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
              setUserData((prev) => ({
                ...prev,
                role: rolePicked,
              }))
            }
            placeholder="Роль"
            styles={customStyles}
          />
        ) : (
          ""
        )}
        <div className={styles.balanceWrapper}>
          <p className={styles.text}>{`Баланс: ${edit ? "" : user.balance}`}</p>
          {edit ? (
            <input
              type="number"
              className={styles.inputUserBalance}
              placeholder={user.balance}
              value={userData.balance}
              onChange={(e) =>
                setUserData((prev) => ({
                  ...prev,
                  balance: +e.target.value,
                }))
              }
            />
          ) : (
            ""
          )}
        </div>
        <div
          className={edit ? "" : styles.edit}
          onClick={(e) => handleEdit(e)}
        ></div>
        <div
          className={edit ? styles.accept : ""}
          onClick={(e) => handleAccept(e)}
        ></div>
        <div className={styles.delete} onClick={(e) => handleDelete(e)}></div>
      </div>
    </div>
  );
};

export default UserCard;
