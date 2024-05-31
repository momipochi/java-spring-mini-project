import { Button } from "@mui/material";
import { Link } from "react-router-dom";

function PlaylistButton() {
  return (
    <Link to={"/playlists"}>  
      <Button variant="outlined">My Playlists</Button>
    </Link>
  );
}

export default PlaylistButton;
