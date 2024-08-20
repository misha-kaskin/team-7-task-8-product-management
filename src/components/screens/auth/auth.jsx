import styles from './auth.module.css'
import { useForm } from 'react-hook-form'

const Auth = ({ activeBlock, setActive, logged, setLogged }) => {
  const { register, reset, handleSubmit } = useForm({
    mode: 'onChange'
  })
  return (
    <div className={activeBlock ? styles.authBlockActive : styles.authBlock} onClick={() => setActive(false)}>
      <form className={activeBlock ? styles.authContentActive : styles.authContent} onClick={e => e.stopPropagation()}>
        <div>
          <p className={styles.hint}>Логин</p>
          <input {
            ...register('login', { required: true })
          } type="text" id="login" placeholder='Логин' />
        </div>
        <div>
          <p className={styles.hint}>Пароль</p>
          <input {
            ...register('password', { required: true })
          }
            type="password" id="password" placeholder='Пароль' />
        </div>
        <button className={styles.btn}>Войти</button>
      </form>
    </div>
  )
}

export default Auth