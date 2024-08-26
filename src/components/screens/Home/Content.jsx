import Item from "../../Item/Item";
import styles from "./Content.module.css";
import { useQuery } from "@tanstack/react-query";
import { GetItemService } from "../../../services/getItem.service.js";
import { useContext, useEffect, useState } from "react";
import AddItem from "../../Item/addItem.jsx";
import { Context } from "../../../main.jsx";
import ItemService from "../../../services/item.Service.js";

function Content() {
  const [userData, setUserData] = useState("");
  const { store } = useContext(Context);
  const [items, setItems] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [options, setOptions] = useState(0);
  const [filteredItems, setFilteredItems] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await ItemService.getAll();
      setItems(data);
      setFilteredItems(data);
      console.log(data);
    };
    fetchData();
    if (localStorage.getItem("user")) {
      setUserData(JSON.parse(localStorage.getItem("user"))["role"]);
    }
  }, [userData]);

  useEffect(() => {
    if (options == 0) {
      setFilteredItems(items);
    }
    if (options == 1) {
      const filter = [];
      items.map((item) => (item.type == "Мерч" ? filter.push(item) : []));
      setFilteredItems(filter);
    }
    if (options == 2) {
      const filter = [];
      items.map((item) =>
        item.type == "Ит-артефакт" ? filter.push(item) : ""
      );
      setFilteredItems(filter);
    }
  }, [options]);

  return (
    <>
      <div className={styles.homePage}>
        <div className={styles.filters}>
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
            disabled={items ? false : true}
          >
            Мерч
          </button>
          <button
            className={options == 2 ? styles.filterBtnActive : styles.filterBtn}
            onClick={(e) => {
              setOptions(2);
            }}
            disabled={items ? false : true}
          >
            Ит-артефакты
          </button>
        </div>
        <div className={styles.products}>
          {filteredItems ? (
            filteredItems.map((item) => <Item key={item.itemId} item={item} />)
          ) : (
            <p>Загрузка</p>
          )}
          {userData == "ADMIN" ? <AddItem /> : ""}
        </div>
      </div>
    </>
  );
}

export default Content;
