import { Button } from "@mui/material";
import TableCell from "@mui/material/TableCell";
import TableRow from "@mui/material/TableRow";
import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import { IPlaylist } from "../interfaces/Interfaces";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";

function Row(props: { row: IPlaylist }) {
  const { row } = props;
  const navigate = useNavigate();
  return (
    <React.Fragment>
      <TableRow sx={{ "& > *": { borderBottom: "unset" } }}>
        <TableCell align="center">{row.id}</TableCell>
        <TableCell align="left" component="th" scope="row">
          {row.playlistName}
        </TableCell>
        <TableCell align="center">{row.size}</TableCell>
        <TableCell align="left">
          <Link to={`/playlists/product/${row.id}`}>
            <Button variant="outlined" color="secondary">
              Details
            </Button>
          </Link>
            <Button
              variant="outlined"
              color="secondary"
              startIcon={<ShareOutlinedIcon />}
            >
              Share
            </Button>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

export default Row;
