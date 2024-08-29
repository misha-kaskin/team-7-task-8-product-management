import { useEffect, useState } from "react";
import styles from "./adminUsers.module.css";
import UserService from "../../../Auth/services/UserService";
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

const noUserData = {
  userId: "",
  name: "",
  role: "",
  balance: "",
};

const UserTableData = ({ user, status, setStatus }) => {
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
    const bool = !edit;
    setEdit(bool);
    console.log(userData);
    const fetchData = async () => {
      const response = await UserService.edit(
        userData.userId,
        userData.name,
        userData.balance,
        userData.role
      );
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
                // styles={customStyles}
              />
              <div
                className={edit ? styles.accept : styles.edit}
                onClick={(e) => setEdit(!edit)}
              ></div>
            </>
          ) : (
            <>
              {user.role}{" "}
              <div
                className={edit ? styles.accept : styles.edit}
                onClick={(e) => setEdit(!edit)}
              ></div>
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
export default UserTableData;
