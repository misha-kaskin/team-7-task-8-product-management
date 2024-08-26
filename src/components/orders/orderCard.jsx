import CartItem from "../Cart/cartItem";
import styles from "./orderCard.module.css";

const OrderCard = ({ order }) => {
  return (
    <div className={styles.cart}>
      <p className={styles.header}>{order ? `Заказ №${order.orderId}` : 'Загрузка'}</p>
      <div className={styles.items}>
        {order ? (
          order.items.map((item) => <CartItem key={item.itemId} item={item} />)
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
          <p className={styles.price}>2000</p>
        </div>
      </div>
    </div>
  );
};

export default OrderCard;
