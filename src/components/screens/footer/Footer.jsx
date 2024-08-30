import { Link } from 'react-router-dom'
import styles from './Footer.module.css'

function Footer() {

  return (
    <>
      <div className={styles.footerBar}>
        <div className={styles.content}>
          <div className={styles.icon} />
          <p>Холдинг Т1 © 2011–2024</p>
          <div className={styles.info}>
            <Link to="/telegram" target='_blank'>
              <div className={styles.telegramIcon}>
              </div>
            </Link>

          </div>
        </div>
      </div>
    </>
  )
}

export default Footer