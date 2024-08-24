import { BrowserRouter, Route, Routes } from "react-router-dom";
import Content from "../screens/Home/Content";
import LoginForm from "../../Auth/components/LoginForm";
import Header from "../screens/header/Header";
import ItemPage from "../screens/itemPage/itemPage";
import Footer from "../screens/footer/Footer";
import { observer } from "mobx-react-lite";
import AddItemPage from "../screens/itemPage/addItemPage";
import EditItemPage from "../screens/itemPage/editItemPage";
import ErrorPage from "../screens/Error/errorPage";
import Cart from "../Cart/cart";
import AdminUsers from "../admin/users/adminUsers";

const Router = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route element={<Content />} path="/" />
        <Route element={<ErrorPage />} path="*" />
        <Route element={<LoginForm />} path="/login" />
        <Route element={<ItemPage />} path="/item/:itemId" />
        <Route element={<AddItemPage />} path="/add" />
        <Route element={<EditItemPage />} path="/edit/:itemId" />
        <Route element={<Cart />} path="/cart" />
        <Route element={<AdminUsers />} path="/admin/users"/>
      </Routes>
      <Footer />
    </BrowserRouter>
  );
};

export default observer(Router);
