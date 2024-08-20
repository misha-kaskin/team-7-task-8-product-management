import { BrowserRouter, Route, Routes } from "react-router-dom";
import Content from "../screens/Home/Content";
import LoginForm from "../../Auth/components/LoginForm";
import Header from "../screens/header/Header";
import ItemPage from "../screens/itemPage/itemPage";
import Footer from "../screens/footer/Footer";
import { observer } from "mobx-react-lite";

const Router = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route element={<Content />} path="/" />
        <Route element={<LoginForm />} path="/login" />
        <Route element={<ItemPage />} path="/item/:id" />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
};

export default observer(Router);
