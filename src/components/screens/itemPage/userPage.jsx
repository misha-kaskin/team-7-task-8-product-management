import styles from "./itemPage.module.css";

const sizeList = ["XS", "S", "M", "L", "XL", "XXL", "One"];

const UserItemPage = ({ item, count, setCount, size, setSize }) => {
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
              item.sizes.map((currentSize, index) =>
                currentSize > 0 ? (
                  <button
                    key={index}
                    className={
                      sizeList.indexOf(size) == index
                        ? styles.sizeBtnActive
                        : styles.sizeBtn
                    }
                    onClick={() => {
                      setSize(sizeList[index]);
                    }}
                  >
                    {sizeList[index]}
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
                ? item.sizes[sizeList.indexOf(size)] != undefined
                  ? `Осталось ${item.sizes[sizeList.indexOf(size)]} штук`
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
                item.sizes
                  ? count < item.sizes[sizeList.indexOf(size)]
                    ? false
                    : true
                  : true
              }
            >
              <div className={styles.plus}></div>
            </button>
          </div>
        </div>
        <p>{item.description}</p>
        <button className={styles.addToCart}>Добавить в корзину</button>
      </div>
    </div>
  );
};

export default UserItemPage;
