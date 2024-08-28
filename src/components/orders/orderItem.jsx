import { useEffect, useState } from "react";
import styles from "../Cart/cartItem.module.css";

const itemDataDefault = {
  itemId: 0,
  sizes: {
    sizeId: 0,
    count: 0,
  },
};

const OrderItem = ({ item, size }) => {
  const [itemData, setItemData] = useState(itemDataDefault);
  const [count, setCount] = useState(size.count);

  
  useEffect(() => {
    setItemData((prev) => ({
      ...prev,
      itemId: item.itemId,
      sizes: size,
    }));
  }, [item, size]);

  const handleAddItem = (e) => {
    e.preventDefault();
    setCount(count+1)
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));    
  };

  const handleRemoveItem = (e) => {
    e.preventDefault();
    setCount(count-1)
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));
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
          <p className={styles.textAmount}>{`Количество: ${count}`}</p>
          {/* <div className={styles.counter}>
            <p style={{ fontSize: "14px" }}>{count}</p>
          </div> */}
        </div>
        <div className={styles.textSize}>Размер: {size.title}</div>
        <div className={styles.bottom}>
          <p className={styles.price}>{item.price}</p>
        </div>
      </div>
    </div>
    </>
  );
};

export default OrderItem;
