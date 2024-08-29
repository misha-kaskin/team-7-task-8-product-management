import { useState } from "react";
import styles from "./userOrder.module.css";

const AdminTableOrder = ({ order }) => {
  const [hover, setHover] = useState(false);
  const data = order.orderDate.split('-')
  return (
    <tr key={order.orderId}>
      <td>{order.orderId}</td>
      <td>{order.status.title}</td>
      <td>{`${data[2]}.${data[1]}.${data[0]}`}</td>
      <td>{order.secondName}</td>
      <td>{order.firstName}</td>
      <td>{order.lastName}</td>
      <td>{order.address}</td>
      <td>{}</td>
      {/* <td>{user.login}</td>
      <td className={styles.roleColumn} onMouseEnter={e => setHover(true)} onMouseLeave={e => setHover(false)}>
        {hover ? (<>{user.role} <div className={styles.edit}></div></>): `${user.role}`}
      </td>
      <td>{user.balance}</td> */}
    </tr>
  );
};
export default AdminTableOrder;
