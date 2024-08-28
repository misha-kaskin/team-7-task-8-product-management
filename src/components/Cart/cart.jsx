import { useEffect, useState } from "react";
import styles from "./cart.module.css";
import ItemService from "../../services/item.Service";
import CartItem from "./cartItem.jsx";

const noUserData = {
  name: "",
  surname: "",
  middleName: "",
  adress: "",
};

const Cart = () => {
  const [data, setData] = useState("");
  const [payment, setPayment] = useState(0);
  const [userData, setUserData] = useState(noUserData);
  const [editBool, setEditBool] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      const userId = JSON.parse(localStorage.getItem("user"))["userId"];
      const responseData = await ItemService.getCart(userId);
      setUserData((prev) => ({
        ...prev,
        userId: +userId,
        items: [],
      }));
      // if (responseData != data) {
      //   setEditBool(false);
      // }
      setData(responseData);

      let amount = 0;
      let paymentVar = 0;
      responseData.items.map((item) => {
        item.sizes.map((size) => {
          amount += size.count;
        });
        paymentVar += amount * item.price;
        amount = 0;
      });
      setPayment(paymentVar);
    };
    fetchData();
  }, [editBool]);

  const handleUpload = async (e) => {
    const sendCart = await ItemService.order(
      userData.userId,
      userData.name,
      userData.surname,
      userData.middleName,
      userData.adress,
      userData.items,
      1
    );
  };

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
            data.items.map((item) =>
              item.sizes.map((size) => (
                <CartItem
                  key={`${item.itemId}${size.sizeId}`}
                  item={item}
                  size={size}
                  editBool={editBool}
                  setEditBool={setEditBool}
                />
              ))
            )
          ) : (
            <p>Загрузка</p>
          )}
        </div>
        <div className={styles.bottomPrice}>
          <div className={styles.lineWrapper}>
            <div className={styles.line}></div>
          </div>
          <div className={styles.bottom}>
            <p className={styles.header}>К оплате</p>
            <p className={styles.price}>{payment}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
