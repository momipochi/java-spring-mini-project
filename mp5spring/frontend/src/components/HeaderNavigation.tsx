import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import PlaylistButton from "./PlaylistButton";
import headernavigation from "../assets/styles/headernavgiation.module.css"

function HeaderNavigation() {
  return (
    <div>
      <nav className={headernavigation.ulContainer}>
        <ul className={headernavigation.navbar}>
          <li>
            <Button variant="outlined">Uploade video</Button>
          </li>
          <li>
            <Button variant="outlined">My videos</Button>
          </li>
          <li>
            <PlaylistButton></PlaylistButton>
          </li>
          <li>
            <Button variant="outlined">Create playlist</Button>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default HeaderNavigation;
