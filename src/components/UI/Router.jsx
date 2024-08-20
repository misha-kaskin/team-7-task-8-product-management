import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Content from '../screens/Home/Content'
import Auth from '../screens/auth/auth'
import Header from '../screens/header/Header'
import ItemPage from '../screens/itemPage/itemPage'
import Footer from '../screens/footer/Footer'


const Router = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route element={<Content />} path='/' />
        <Route element={<Auth />} path='/login' />
        <Route element={<ItemPage />} path='/item/:id' />

      </Routes>
      <Footer />
    </BrowserRouter>
  )
}

export default Router