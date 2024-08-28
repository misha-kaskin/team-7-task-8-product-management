import { useEffect, useState } from "react";
import styles from "./cartItem.module.css";
import ItemService from "../../services/item.Service";

const itemDataDefault = {
  itemId: 0,
  sizes: {
    sizeId: 0,
    count: 0,
  },
};

const CartItem = ({ item, size, setEditBool }) => {
  const [itemData, setItemData] = useState(itemDataDefault);
  const [editItemData, setEditItemData] = useState({});
  const [count, setCount] = useState(0);

  useEffect(() => {
    setItemData((prev) => ({
      ...prev,
      itemId: item.itemId,
      sizes: size,
    }));
    setEditItemData((prev) => ({
      ...prev,
      items: { itemId: item.itemId, sizes: size },
    }));
    setCount(size.count)
    console.log(count);
  }, [item, size]);

  useEffect(() => {
    const sizesArray = [{ sizeId: itemData.sizes.sizeId, count: count }];
    setEditItemData((prev) => ({
      ...prev,
      items: { itemId: itemData.itemId, sizes: sizesArray },
    }));
    setEditBool(true)
  }, [count]);

  useEffect(() => {
    console.log(editItemData);
    const editCartItem = async () => {
      if (
        itemData.itemId > 0 &&
        editItemData.items != null &&
        editItemData.items.itemId != 0 &&
        Array.isArray(editItemData.items.sizes)
      ) {
        const itemsArray = [editItemData.items];
        const userId = JSON.parse(localStorage.getItem("user"))["userId"];
        const sendData = await ItemService.addToCart(userId, itemsArray);
      }
    };
    editCartItem();
  }, [editItemData]);

  const handleAddItem = (e) => {
    e.preventDefault();
    setCount(count + 1);
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));
  };

  const handleRemoveItem = (e) => {
    e.preventDefault();
    setCount(count - 1);
    setItemData((prev) => ({
      ...prev,
      sizes: { ...prev.sizes, count: count },
    }));
  };

  const handleDeleteItem = async (e) => {
    e.preventDefault()
    const userId = JSON.parse(localStorage.getItem("user"))["userId"];
    const response = await ItemService.deleteItemCart(userId, item.itemId, size.sizeId)
  };
  return (
    <>
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
          <div className={styles.textSize}>Размер: {size.title}</div>
          <div className={styles.bottom}>
            <p className={styles.price}>{item.price}</p>
            <div
              className={styles.delete}
              onClick={(e) => handleDeleteItem(e)}
            ></div>
          </div>
        </div>
      </div>
    </>
  );
};

export default CartItem;
