import { Link } from "react-router-dom";
import styles from "./Item.module.css";
import { useContext, useEffect, useState } from "react";
import { Context } from "../../main";

function AddItem() {
  const { store } = useContext(Context);
  const [isLogged, setIsLogged] = useState(store.isAuth);

  useEffect(() => {
    setIsLogged(store.isAuth);
  });

  if (store.isAuth) {
    if (JSON.parse(localStorage.getItem("user"))["role"] == "ADMIN") {
      return (
        <>
          <div className={styles.itemPlace}>
            <Link
              to={`/add`}
              style={{
                backgroundColor: "#d9d9d9",
              }}
            >
              <div className={styles.item}>
                <div className={styles.itemPlus}></div>
              </div>
            </Link>
            <Link to={`/add`} className={styles.itemName}>
              Добавить товар
            </Link>
          </div>
        </>
      );
    }
  }
}

export default AddItem;
