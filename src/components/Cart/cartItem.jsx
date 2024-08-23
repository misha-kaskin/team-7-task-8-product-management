const CartItem = ({ item }) => {
  return (
    <div>
      <div className={styles.image} />
      <div className={styles.info}>
        <p>Название</p>
        <div className={styles.amount}>
          <p>Количество</p>
          <div className={styles.amountBtn}></div>
        </div>
        <div className={styles.size}>Размер:</div>
        <div className={styles.bottom}>
          <p>PRICE</p>
          <button>Мусорка</button>
        </div>
      </div>
    </div>
  );
};

export default CartItem;
