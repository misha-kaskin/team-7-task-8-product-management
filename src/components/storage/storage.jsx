import { useEffect, useState } from "react";
import styles from "../admin/users/adminUsers.module.css";
import ItemService from "../../services/item.service";
import StorageRow from "./storageRow";

const Storage = () => {
  const [itemsData, setitemsData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filtereditems, setFiltereditems] = useState([]);
  const [status, setStatus] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      const response = await ItemService.getWareHouse();
      response.data.sort((a, b) => {
        return a.itemId - b.itemId;
      });
      setitemsData(response.data);
      setFiltereditems(response.data);
    };
    fetchData();
  }, [status]);

  useEffect(() => {
    if (options == 0) {
      setFiltereditems(itemsData);
    }
    if (options == 1) {
      const filter = [];
      itemsData.map((item) => (item.role == "ADMIN" ? filter.push(item) : ""));
      setFiltereditems(filter);
    }
    if (options == 2) {
      const filter = [];
      itemsData.map((item) =>
        item.role == "MANAGER" ? filter.push(item) : ""
      );
      setFiltereditems(filter);
    }
    if (options == 3) {
      const filter = [];
      itemsData.map((item) => (item.role == "item" ? filter.push(item) : ""));
      setFiltereditems(filter);
    }
    console.log(filtereditems);
  }, [options]);

  return (
    <div className={styles.content}>
      <div className={styles.info}>
        <h1>Склад</h1>
        <input
          type="text"
          className={styles.search}
          value={searchTerm}
          placeholder="Поиск"
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button
          className={options == 0 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(0);
          }}
        >
          Все
        </button>
        <button
          className={options == 1 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(1);
          }}
        >
          В продаже
        </button>
        <button
          className={options == 2 ? styles.filterBtnActive : styles.filterBtn}
          onClick={(e) => {
            setOptions(2);
          }}
        >
          Снято с продажи
        </button>
        <button
          className={options == 3 ? styles.filterBtnActive : styles.filterBtn}
          style={{ marginRight: "20px" }}
          onClick={(e) => {
            setOptions(3);
          }}
        >
          Архивированы
        </button>
      </div>
      <div className={styles.users}>
        <table className={styles.tableHeader}>
          <thead>
            <tr>
              <th className={styles.headerBorder}>Id</th>
              <th className={styles.headerBorder}>Статус</th>
              <th className={styles.headerBorder}>Тип</th>
              <th className={styles.headerBorder}>Название</th>
              <th className={styles.headerBorder}>XS</th>
              <th className={styles.headerBorder}>S</th>
              <th className={styles.headerBorder}>M</th>
              <th className={styles.headerBorder}>L</th>
              <th className={styles.headerBorder}>XL</th>
              <th className={styles.headerBorder}>XXL</th>
              <th className={styles.headerBorder}>ONE</th>
              <th className={styles.headerBorder}>Цена</th>
            </tr>
          </thead>
          <tbody>
            {itemsData
              ? filtereditems
                  .filter((item) => {
                    return searchTerm.toLowerCase === "" ||
                      !item.login ||
                      !item.name ||
                      !item.surname ||
                      !item.middleName
                      ? item
                      : item.login
                          .toLowerCase()
                          .includes(searchTerm.toLowerCase()) ||
                          item.name
                            .toLowerCase()
                            .includes(searchTerm.toLowerCase()) ||
                          item.surname
                            .toLowerCase()
                            .includes(searchTerm.toLowerCase());
                  })
                  .map((item) => (
                    <StorageRow
                      key={item.itemId}
                      item={item}
                      status={status}
                      setStatus={setStatus}
                      styleData={styles}
                    />
                  ))
              : "Пользователи не найдены"}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Storage;
