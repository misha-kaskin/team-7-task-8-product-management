import { createContext, StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import "./assets/styles/global.css";
import Router from "./components/UI/Router.jsx";
import Store from "./Auth/store/store.js";

const store = new Store();

export const Context = createContext({
  store,
});

const queryClient = new QueryClient();

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <Context.Provider
      value={{
        store,
      }}
    >
      <QueryClientProvider client={queryClient}>
        <Router />
      </QueryClientProvider>
    </Context.Provider>
  </StrictMode>
);
