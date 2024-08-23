import Item from "../../Item/Item";
import styles from "./Content.module.css";
import { useQuery } from "@tanstack/react-query";
import { GetItemService } from "../../../services/getItem.service.js";
import { useContext, useEffect, useState } from "react";
import AddItem from "../../Item/addItem.jsx";
import { Context } from "../../../main.jsx";
import ItemService from "../../../services/item.Service.js";
import Filter from "../../Filter/filter.jsx";

function Content() {
  // const {data, isLoading, error} = useQuery(["items"], () => ItemService.getAll())
  const { store } = useContext(Context);
  const [items, setItems] = useState('');
  let types = [];

  useEffect(() => {
    const fetchData = async () => {
      const data = await ItemService.getAll();
      setItems(data);
    };
    fetchData();
  }, []);

  for (let item in items) {
    if (!types.includes(item.type)) {
      types.push(item.type);
    }
  }

  return (
    <>
      <div className={styles.homePage}>
        <div className={styles.filters}>
          <input type="text" id="search" className={styles.search} />
          <Filter items={items} />
        </div>
        <div className={styles.products}>
          {items ? items.map((item) => (
            <Item key={item.itemId} item={item} />)) : <p>Загрузка</p>
          }
          <AddItem />
        </div>
      </div>
    </>
  );
}

export default Content;
