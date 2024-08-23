import { useState } from "react";
import styles from "./cart.module.css";

const noUserData = {
  name: "",
  surname: "",
  middleName: "",
  adress: "",
};

const Cart = () => {
  const [data, setData] = useState();
  const [userData, setUserData] = useState(noUserData);

  return (
    <div className={styles.content}>
      <div className={styles.info}>
        <h1 className={styles.title}>Оформление заказа</h1>
        <div>
          <p className={styles.text}>Фамилия</p>
          <input
            onChange={(e) =>
              setUserData((prev) => ({
                ...prev,
                surname: e.target.value,
              }))
            }
            value={userData.surname}
            className={styles.inputData}
            type="text"
          />
        </div>
        <div>
          <p className={styles.text}>Имя</p>
          <input
            onChange={(e) =>
              setUserData((prev) => ({
                ...prev,
                name: e.target.value,
              }))
            }
            value={userData.name}
            className={styles.inputData}
            type="text"
          />
        </div>
        <div>
          <p className={styles.text}>Отчество</p>
          <input
            onChange={(e) =>
              setUserData((prev) => ({
                ...prev,
                middleName: e.target.value,
              }))
            }
            value={userData.middleName}
            className={styles.inputData}
            type="text"
          />
        </div>
        <div>
          <p className={styles.text}>Адрес</p>
          <input
            onChange={(e) =>
              setUserData((prev) => ({
                ...prev,
                adress: e.target.value,
              }))
            }
            value={userData.adress}
            className={styles.inputData}
            type="text"
          />
        </div>
        <p className={styles.payment}>Оплата при получении</p>
        <button
          onClick={(e) => {
            e.preventDefault();
            handleUpload();
          }}
          className={styles.addItem}
        >
          Оформить заказ
        </button>
      </div>
      <div className={styles.cart}>
        <p className={styles.header}>Корзина</p>
        <div className={styles.items}>
          {data ? (
            data.map((item) => (
              // Сменить на CartItem
              <Item key={item.itemId} item={item} />
            ))
          ) : (
            <p>Загрузка</p>
          )}
        </div>
        <div className={styles.lineWrapper}>
          <div className={styles.line}></div>
        </div>
        <div className={styles.bottom}>
          <p className={styles.header}>К оплате</p>
          <p className={styles.price}>2000</p>
        </div>
      </div>
    </div>
  );
};

export default Cart;
