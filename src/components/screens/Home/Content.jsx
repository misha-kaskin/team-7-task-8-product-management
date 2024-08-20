import Item from '../../Item/Item'
import styles from './Content.module.css'
import { useQuery } from '@tanstack/react-query'
import { ItemService } from '../../../services/getItem.service.js'
import { useEffect, useState } from 'react'

function Content() {
  // const {data, isLoading, error} = useQuery(["items"], () => ItemService.getAll())


  const [items, setItems] = useState([])

  useEffect(() => {
    const fetchData = async () => {
      const data = await ItemService.getAll()
      setItems(data)
    }
    fetchData()
  }, [])

  return (
    <>
      <div className={styles.homePage}>
        <div className={styles.filters}>
          <input type="text" id="search" className={styles.search} />
          <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Velit magni harum commodi porro inventore id quaerat, nobis placeat non quasi sit repellendus, earum nisi ad consequatur adipisci itaque corrupti distinctio?</p>
        </div>
        <div className={styles.products}>
          {items.map(item => (
            <Item key={item.id} item={item} />
          ))}
        </div>
      </div>
    </>
  )
}

export default Content
