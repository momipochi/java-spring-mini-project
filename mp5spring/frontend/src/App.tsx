import { useState } from "react";
import logo from "./logo.svg";
import "./App.css";
import HeaderNavigation from "./components/HeaderNavigation";
import Routings from "./routes/Routings";
import { QueryClient, QueryClientProvider } from "react-query";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient} contextSharing={true}>
      <div className="App">
        <HeaderNavigation></HeaderNavigation>
        <Routings></Routings>
      </div>
    </QueryClientProvider>
  );
}

export default App;
