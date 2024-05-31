import { Route, Routes } from "react-router-dom";
import Playlists from "../pages/Playlists";
import SubRow from "../pages/SubRow";

const Routings = () => {
  return (
    <Routes>
      <Route path="/playlists" element={<Playlists/>} />
      <Route path="/playlists/product/:id" element={<SubRow/>} />
    </Routes>
  );
};

export default Routings;
