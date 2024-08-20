import { Link } from 'react-router-dom'
import styles from './Item.module.css'


function Item({item}) {

  return (
    <>
      <div className={styles.itemPlace}>
          <Link to={`/item/${item.id}`} style={{
            backgroundColor: "#d9d9d9"
          }}>
            <div style={{backgroundImage: `url(${item.img})`}}
            className={styles.item}/>
          </Link>
          <Link to={`/item/${item.id}`} className={styles.itemName}>{item.name}</Link>
          <p className={styles.itemPrice}>{item.price}</p>
      </div>
    </>
  )
}

export default Item