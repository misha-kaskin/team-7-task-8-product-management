import { useParams } from "react-router-dom";
import styles from "./itemPage.module.css";
import { useEffect, useState } from "react";
import { ItemService } from "../../../services/getItem.service";

const ItemPage = () => {
  const { id } = useParams();
  const [item, setItem] = useState({});
  const [count, setCount] = useState(1);
  const [size, setSize] = useState("");

  useEffect(() => {
    if (!id) return;

    const fetchData = async () => {
      const data = await ItemService.getById(id);
      setItem(data);
    };
    fetchData();
  }, [id]);

  return (
    <div className={styles.content}>
      <div
        style={{ backgroundImage: `url(${item.img})` }}
        className={styles.itemImage}
      />
      <div className={styles.info}>
        <h1 className={styles.itemName}>{item.name}</h1>
        <p className={styles.itemPrice}>{item.price}</p>
        <div>
          <p>Выберите размер</p>
          <div className={styles.sizes}>
            {item.sizes ? (
              item.sizes.map((currentSize, index) => (
                <button
                  key={index}
                  className={
                    item.sizes.indexOf(size) == index
                      ? styles.sizeBtnActive
                      : styles.sizeBtn
                  }
                  onClick={() => {
                    setSize(currentSize);
                  }}
                >
                  {currentSize}
                </button>
              ))
            ) : (
              <p>Loading</p>
            )}
          </div>
        </div>
        <div className={styles.ammountBlock}>
          <div>
            <p className={styles.ammountText}>Выберите количество</p>
            <p className={styles.ammountStatus}>
              {item.available
                ? item.available[size] != undefined
                  ? `Осталось ${item.available[size]} штук`
                  : `Выберите размер`
                : "Загрузка"}
            </p>
          </div>
          <div className={styles.counter}>
            <button
              className={styles.counterBtn}
              onClick={() => setCount(count - 1)}
              disabled={count > 1 ? false : true}
            >
              <div className={styles.minus}></div>
            </button>
            <p style={{ fontSize: "20px" }}>{count}</p>
            <button
              className={styles.counterBtn}
              onClick={() => setCount(count + 1)}
              disabled={
                item.available
                  ? count < item.available[size]
                    ? false
                    : true
                  : true
              }
            >
              <div className={styles.plus}></div>
            </button>
          </div>
        </div>
        <p>90% Хлопок, 10% полиэстер</p>
        <button className={styles.addToCart}>Добавить в корзину</button>
      </div>
    </div>
  );
};

export default ItemPage;
