import { useEffect, useState } from "react";
import styles from "./userOrder.module.css";
import ItemService from "../../services/item.service";
import Select from "react-select";

const optionsRole = [
  {
    value: "1",
    label: "В работе",
  },
  {
    value: "Собран",
    label: "Собран",
  },
  {
    value: "Выдан",
    label: "Выдан",
  },
  {
    value: "Отклонен",
    label: "Отклонен",
  },
];

const customStyles = {
  input: (provided, state) => ({
    ...provided,
    color: "black",
    maxHeight: "30px",
    maxWidth: "100px",
    fontSize: "14px",
  }),
  container: (provided, state) => ({
    ...provided,
    color: "black",
    maxHeight: "30px",
    maxWidth: "100px",
    fontSize: "14px",
  }),
};

const AdminTableOrder = ({ order, status, setStatus }) => {
  const [hover, setHover] = useState(false);
  const [orderData, setOrderData] = useState({});
  const [edit, setEdit] = useState(false);
  const data = order.orderDate.split("-");
  const [rolePicked, setRolePicked] = useState(order.status.title);
  const [payment, setPayment] = useState(0);

  useEffect(() => {
    setOrderData(order);
    let amount = 0;
    let paymentVar = 0;
    order.items.map((item) => {
      item.sizes.map((size) => {
        amount += size.count;
      });
      paymentVar += amount * item.price;
      amount = 0;
    });
    setPayment(paymentVar);
  }, [status]);

  const handleAccept = async (e) => {
    e.preventDefault();
    setEdit(!edit);
    const fetchData = async () => {
      const response = await ItemService.setOrderStatus(
        orderData.userId,
        orderData.status.statusId
      );
      setStatus(!status);
    };
    fetchData();
  };

  const getValue = () => {
    return rolePicked ? optionsRole.find((c) => c.value === rolePicked) : "";
  };

  const onChange = (newValue) => {
    setRolePicked(newValue.value);
  };

  return (
    <tr
      key={order.orderId}
      onMouseEnter={(e) => setHover(true)}
      onMouseLeave={(e) => setHover(false)}
    >
      <td>
        {hover ? (
          edit ? (
            <>
              {order.orderId}
              <button
                className={styles.accept}
                onClick={(e) => {
                  handleAccept(e);
                }}
              ></button>
            </>
          ) : (
            <>
              {order.orderId}
              <button
                className={hover ? styles.edit : styles.invisible}
                onClick={(e) => {
                  e.preventDefault();
                  setEdit(!edit);
                }}
              ></button>
            </>
          )
        ) : (
          order.orderId
        )}
      </td>
      <td className={styles.roleColumn}>
        {hover ? (
          edit ? (
            <>
              <Select
                options={optionsRole}
                value={getValue()}
                onChange={onChange}
                required
                onBlur={(e) =>
                  setOrderData((prev) => ({
                    ...prev,
                    status: { ...prev.status, title: rolePicked },
                  }))
                }
                placeholder="Статус"
                styles={customStyles}
              />
            </>
          ) : (
            <>{order.status.title}</>
          )
        ) : (
          <>{order.status.title}</>
        )}
      </td>
      <td>{`${data[2]}.${data[1]}.${data[0]}`}</td>
      <td>{order.secondName}</td>
      <td>{order.firstName}</td>
      <td>{order.lastName}</td>
      <td>{order.address}</td>
      <td>{payment}</td>
    </tr>
  );
};
export default AdminTableOrder;
