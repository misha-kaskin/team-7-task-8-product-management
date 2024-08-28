import { useEffect, useState } from "react";
import styles from "./orderCard.module.css";
import OrderItem from "./orderItem";

const OrderCard = ({ order }) => {
  const [payment, setPayment] = useState(0);
  const data = order.orderDate.split('-')
  console.log(data);

  useEffect(() => {
    let amount = 0;
    let paymentVar = 0;
    order.items.map((item) => {
      item.sizes.map((size) => {
        amount += size.count;
      });
      paymentVar += amount * item.price;
      amount = 0;
    });
    setPayment(paymentVar)
  }, [])
  return (
    <div className={styles.cart}>
      <div className={styles.header}>
      <p className={styles.headerText}>
        {order ? `Заказ №${order.orderId}` : "Загрузка"}
      </p>
      <p className={styles.headerData}>
      {order ? `${data[2]}.${data[1]}.${data[0]}` : "Загрузка"}
      </p>
      </div>
      <div className={styles.items}>
        {order ? (
          order.items.map((item) =>
            item.sizes.map((size) => (
              <OrderItem key={item.itemId} item={item} size={size} />
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
          <p className={styles.headerText}>{order.status.title}</p>
          <p className={styles.price}>{payment}</p>
        </div>
      </div>
    </div>
  );
};

export default OrderCard;
