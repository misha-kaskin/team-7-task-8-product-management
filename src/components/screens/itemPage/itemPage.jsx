import { useParams } from "react-router-dom";
import styles from "./itemPage.module.css";
import { useContext, useEffect, useState } from "react";
import { GetItemService } from "../../../services/getItem.service";
import { Context } from "../../../main";
import UserItemPage from "./userPage";
import AdminItemPage from "./adminItemPage";
import ItemService from "../../../services/item.Service";

const noData = {
  userId: 0,
  items: {
    itemId: 0,
    sizes: {
      sizeId: 0,
      count: 0,
    },
  },
};

const cartItemDefault = {
  size: "",
  count: 1,
};

const ItemPage = () => {
  const { store } = useContext(Context);
  const { itemId } = useParams();
  const [item, setItem] = useState({});
  const [cartItem, setCartItem] = useState(cartItemDefault);
  const [data, setData] = useState(noData);

  useEffect(() => {
    if (!itemId) return;

    const fetchData = async () => {
      const response = await ItemService.getById(itemId);
      setItem(response);
    };

    fetchData();
  }, []);

  const handleAdd = (e) => {
    e.preventDefault();
    const sizesArray = [data.items.sizes]
    setData((prev) => ({
      ...prev,
      items: {...prev.items, sizes: sizesArray}
    }))
    const itemsArray = [data.items]
    const fetchData = async () => {
      const res = await ItemService.addToCart(data.userId, itemsArray);
    };
    fetchData();
    
  };

  useEffect(() => {
    setData((prev) => ({
      items: { ...prev.items, itemId: +itemId },
    }));
    // console.log(data);
  }, [item]);

  useEffect(() => {
    setData((prev) => ({
      ...prev,
      userId: JSON.parse(localStorage.getItem("user"))["userId"],
      items: 
        {
          ...prev.items,
          sizes: [{ sizeId: +cartItem.size, count: +cartItem.count }],
        },
    }));
  }, [cartItem]);

  if (store.isAuth) {
    if (JSON.parse(localStorage.getItem("user"))["role"] == "ADMIN") {
      return (
        <AdminItemPage
          item={item}
          cartItem={cartItem}
          setCartItem={setCartItem}
          handleAdd={handleAdd}
        />
      );
    } else {
      return (
        <UserItemPage
          item={item}
          cartItem={cartItem}
          setCartItem={setCartItem}
          handleAdd={handleAdd}
        />
      );
    }
  } else {
    return (
      <UserItemPage
        item={item}
        cartItem={cartItem}
        setCartItem={setCartItem}
        handleAdd={handleAdd}
      />
    );
  }
};

export default ItemPage;
