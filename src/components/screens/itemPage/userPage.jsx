import { useEffect, useState } from "react";
import styles from "./itemPage.module.css";

const sizeList = ["XS", "S", "M", "L", "XL", "XXL", "One"];

const UserItemPage = ({ item, cartItem, setCartItem, handleAdd }) => {
  const [amount, setAmount] = useState("");

  useEffect(() => {
    if (!cartItem.size) {
      return;
    }
    setAmount(item.sizes.find((size) => size.sizeId == cartItem.size).count);
  }, [cartItem]);
  return (
    <div className={styles.content}>
      <div
        style={{ backgroundImage: `url(${item.image})` }}
        className={styles.itemImage}
      />
      <div className={styles.info}>
        <h1 className={styles.itemName}>{item.productName}</h1>
        <p className={styles.itemPrice}>{item.price}</p>
        <div>
          <p>Выберите размер</p>
          <div className={styles.sizes}>
            {item.sizes ? (
              item.sizes.map((currentSize) =>
                currentSize.count > 0 ? (
                  <button
                    key={currentSize.sizeId}
                    className={
                      cartItem.size == currentSize.sizeId
                        ? styles.sizeBtnActive
                        : styles.sizeBtn
                    }
                    onClick={() => {
                      setCartItem((prev) => ({
                        ...prev,
                        size: currentSize.sizeId,
                      }));
                      console.log(cartItem);
                    }}
                  >
                    {currentSize.title}
                  </button>
                ) : (
                  ""
                )
              )
            ) : (
              <p>Loading</p>
            )}
          </div>
        </div>
        <div className={styles.ammountBlock}>
          <div>
            <p className={styles.ammountText}>Выберите количество</p>
            <p className={styles.ammountStatus}>
              {item.sizes
                ? amount
                  ? `Осталось ${amount} штук`
                  : `Выберите размер`
                : "Загрузка"}
            </p>
          </div>
          <div className={styles.counter}>
            <button
              className={styles.counterBtn}
              onClick={() =>
                setCartItem((prev) => ({
                  ...prev,
                  count: cartItem.count - 1,
                }))
              }
              disabled={cartItem.count > 1 ? false : true}
            >
              <div className={styles.minus}></div>
            </button>
            <p style={{ fontSize: "20px" }}>{cartItem.count}</p>
            <button
              className={styles.counterBtn}
              onClick={() =>
                setCartItem((prev) => ({
                  ...prev,
                  count: cartItem.count + 1,
                }))
              }
              disabled={
                item.sizes ? (cartItem.count < amount ? false : true) : true
              }
            >
              <div className={styles.plus}></div>
            </button>
          </div>
        </div>
        <p>{item.description}</p>
        <button className={styles.addToCart} onClick={(e) => handleAdd(e)}>
          Добавить в корзину
        </button>
      </div>
    </div>
  );
};

export default UserItemPage;
