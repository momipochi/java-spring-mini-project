import { useQuery } from "react-query";
import { getPlaylists } from "../api/Api";
import PlaylistTable from "./PlaylistTable";

const Playlists = () => {
  // return <div>Hi</div>
  return (
    <div>
      <h2>My playlists</h2>
      <PlaylistTable></PlaylistTable>
    </div>
  );
};

export default Playlists;
