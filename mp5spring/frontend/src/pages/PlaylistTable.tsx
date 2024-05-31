import {
  Paper, Table, TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@mui/material";
import { useEffect } from "react";
import { useQuery } from "react-query";
import { getPlaylists } from "../api/Api";
import { IPlaylist } from "../interfaces/Interfaces";
import Row from "./Row";

type Playlist = {
  id: number;
  playlistName: string;
  size: number;
};

const PlaylistTable = () => {
  const {
    data: playlistsData,
    isError: playlistsIsError,
    isLoading: playlistsLoading,
    error: playlistsError,
    refetch: refetchPlaylists,
  } = useQuery("playlists", getPlaylists, {
    refetchOnWindowFocus: false,
    enabled: false,
  });
  useEffect(() => {
    refetchPlaylists();
  }, []);
  if (playlistsLoading) {
    return (
      <div>Loading..</div>
    );
  }
  if (playlistsIsError) {
    return (
      <div>Something went wrong..</div>
    );
  }
  return (
    <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="center">Playlist ID</TableCell>
            <TableCell align="left">Name of playlist</TableCell>
            <TableCell align="center">Playlist size</TableCell>
            <TableCell />
          </TableRow>
        </TableHead>
        <TableBody>{playlistsData?.map((row: IPlaylist) => (
            <Row key={row.id} row={row}/>
          ))}</TableBody>
      </Table>
    </TableContainer>
  );
};

export default PlaylistTable;
