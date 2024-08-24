import { useEffect, useState } from "react";
import styles from "./cartItem.module.css";

const itemDataDefault = {
  itemId: 0,
  sizes: {
    sizeId: 0,
    count: 0,
  },
};

const CartItem = ({ item }) => {
  const [itemData, setItemData] = useState(itemDataDefault);
  const [count, setCount] = useState(item.sizes[0].count);

  useEffect(() => {
    setItemData((prev) => ({
      ...prev,
      itemId: item.itemId,
      sizes: { sizeId: item.sizes[0].sizeId, count: item.sizes[0].count },
    }));
    console.log(itemData);
  }, [item]);

  const handleAddItem = (e) => {
    e.preventDefault();
    setCount(count+1)
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));
    console.log(count);
    
  };

  const handleRemoveItem = (e) => {
    e.preventDefault();
    setCount(count-1)
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));
    console.log(count);
  };

  const handleDeleteItem = (e) => {
  }
  

  return (<>
    <div className={styles.itemCard}>
      <div
        className={styles.image}
        style={{
          backgroundImage: `url(${item.image})`,
        }}
      />
      <div className={styles.info}>
        <p className={styles.text}>{item.productName}</p>
        <div className={styles.amount}>
          <p className={styles.textAmount}>Количество</p>
          <div className={styles.counter}>
            <button
              className={styles.counterBtn}
              onClick={(e) => handleRemoveItem(e)}
              disabled={count > 1 ? false : true}
            >
              <div className={styles.minus}></div>
            </button>
            <p style={{ fontSize: "14px" }}>{count}</p>
            <button
              className={styles.counterBtn}
              onClick={(e) => handleAddItem(e)}
            >
              <div className={styles.plus}></div>
            </button>
          </div>
        </div>
        <div className={styles.textSize}>Размер: {item.sizes[0].title}</div>
        <div className={styles.bottom}>
          <p className={styles.price}>{item.price}</p>
          <div className={styles.delete} onClick={(e) => handleDeleteItem(e)}></div>
        </div>
      </div>
    </div>
    </>
  );
};

export default CartItem;
