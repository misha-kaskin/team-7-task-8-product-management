import { useEffect, useState } from "react";
import styles from "../admin/users/adminUsers.module.css";
import Select from "react-select";
import ItemService from "../../services/item.service";

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
  input: (provided, state) => ({
    ...provided,
    color: "black",
  }),
  container: (provided, state) => ({
    ...provided,
    color: "black",
  }),
};

const noUserData = {
  userId: "",
  name: "",
  role: "",
  balance: "",
};

const StorageRow = ({ user, status, setStatus }) => {
  const [hover, setHover] = useState(false);
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
  }, [status]);

  const handleAccept = async (e) => {
    e.preventDefault();
    setEdit(!edit);
    console.log(userData);
    const fetchData = async () => {
      const response = await ItemService.edit(
        userData.userId,
        userData.name,
        userData.balance,
        userData.role
      );
      setStatus(!status);
    };

    fetchData();
  };

  const getValue = () => {
    return rolePicked ? optionsRole.find((c) => c.value === rolePicked) : "";
  };

  const onChange = (newValue) => {
    setRolePicked(newValue.value);
  };

  return (
    <tr key={user.userId}>
      <td>{user.userId}</td>
      <td>{user.surname}</td>
      <td>{user.name}</td>
      <td>{user.middleName}</td>
      <td>{user.login}</td>
      <td
        className={styles.roleColumn}
        onMouseEnter={(e) => setHover(true)}
        onMouseLeave={(e) => setHover(false)}
      >
        {hover ? (
          edit ? (
            <>
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
              <button
                className={styles.accept}
                onClick={(e) => {
                  handleAccept(e);
                }}
              ></button>
            </>
          ) : (
            <>
              {user.role}{" "}
              <button
                className={styles.edit}
                onClick={(e) => {
                  e.preventDefault();
                  setEdit(!edit);
                }}
              ></button>
            </>
          )
        ) : (
          `${user.role}`
        )}
      </td>
      <td>{user.balance}</td>
    </tr>
  );
};
export default StorageRow;
