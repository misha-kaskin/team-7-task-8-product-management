import { Link } from "react-router-dom";
import styles from "./itemPage.module.css";
import ItemService from "../../../services/item.Service";
import { useEffect, useState } from "react";

const sizeList = ["XS", "S", "M", "L", "XL", "XXL", "One"];

const AdminItemPage = ({ item, cartItem, setCartItem, handleAdd }) => {
  const [amount, setAmount] = useState("");

  useEffect(() => {
    if (!cartItem.size) {
      return;
    }
    setAmount(item.sizes.find((size) => size.sizeId == cartItem.size).count);
  }, [cartItem]);

  const handleDeleteItem = (e) => {
    const body = {
      itemId: item.itemId,
    };
    const fetchData = async () => {
      const data = await ItemService.deleteItem(body);
    };
    fetchData();
  };

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
                  currentSize.sizeId != 7 ? (
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
                          count: 1,
                        }));
                        
                      }}
                    >
                      {currentSize.title}
                    </button>
                  ) : (
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
                          count: 1,
                        }));
                        
                      }}
                    >
                      one
                    </button>
                  )
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
        <div className={styles.btnsBlock}>
          <button className={styles.adminBtns} onClick={(e) => handleAdd(e)}>
            Добавить в корзину
          </button>
          <Link to={`/edit/${item.itemId}`}>
            <button className={styles.adminBtns}>Редактировать</button>
          </Link>
          <button
            className={styles.adminBtns}
            onClick={(e) => handleDeleteItem(e)}
          >
            Удалить товар
          </button>
        </div>
      </div>
    </div>
  );
};

export default AdminItemPage;
