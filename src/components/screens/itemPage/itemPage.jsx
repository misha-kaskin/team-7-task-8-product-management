import { useParams } from "react-router-dom";
import styles from "./itemPage.module.css";
import { useContext, useEffect, useState } from "react";
import { GetItemService } from "../../../services/getItem.service";
import { Context } from "../../../main";
import UserItemPage from "./userPage";
import AdminItemPage from "./adminItemPage";
import ItemService from "../../../services/item.Service";

const ItemPage = () => {
  const { store } = useContext(Context);
  const { itemId } = useParams();
  const [item, setItem] = useState({});
  const [count, setCount] = useState(1);
  const [size, setSize] = useState("");

  useEffect(() => {
    if (!itemId) return;

    const fetchData = async () => {
      const data = await ItemService.getById(itemId);
      setItem(data);
    };
    fetchData();
  }, [itemId]);
  
  if (store.isAuth) {
    if (JSON.parse(localStorage.getItem("user"))["role"] == "ADMIN") {
      return (
        <AdminItemPage
          item={item}
          count={count}
          setCount={setCount}
          size={size}
          setSize={setSize}
        />
      );
    } else {
      return (
        <UserItemPage
          item={item}
          count={count}
          setCount={setCount}
          size={size}
          setSize={setSize}
        />
      );
    }
  } else {
    return (
      <UserItemPage
        item={item}
        count={count}
        setCount={setCount}
        size={size}
        setSize={setSize}
      />
    );
  }
};

export default ItemPage;
