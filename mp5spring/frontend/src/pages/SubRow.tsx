import { Button, Paper, TableContainer } from "@mui/material";
import Box from "@mui/material/Box";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Typography from "@mui/material/Typography";
import { useEffect } from "react";
import {
  useMutation,
  UseMutationResult,
  useQuery,
  useQueryClient,
  useQueryErrorResetBoundary,
  UseQueryResult,
} from "react-query";
import { useParams } from "react-router-dom";
import { getProductsFromPlaylist, removeVideoFromPlaylist } from "../api/Api";
import ProductRecord from "../components/ProductRecord";
import { IProduct } from "../interfaces/Interfaces";

type RemoveParam = {
  playlistId: number;
  videoIndex: number;
};

function SubRow() {
  
  const { id } = useParams();
  if (id == undefined) {
    return <div>Give me a moment...</div>;
  }
  const {
    data: productsData,
    isError: productsIsError,
    isLoading: productsLoading,
    error: productsError,
    refetch: refetchProducts,
  } = useQuery(
    "productsInPlaylist",
    async () => {
      let playlistid: number = id !== undefined ? +id : -1;
      return await getProductsFromPlaylist(playlistid);
    },
    {
      refetchOnWindowFocus: false,
      // enabled: false,
    }
  );
  useEffect(() => {
    refetchProducts();
  }, []);
  if (productsLoading) {
    return <div>Loading</div>;
  }
  if (productsIsError) {
    return <div>Something went wrong..</div>;
  }
  if (productsData == undefined) {
    return <div>Something went wrong..</div>;
  }

  return (
    <div>
      <h2>Details</h2>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Product ID</TableCell>
              <TableCell align="left">Product Name</TableCell>
              <TableCell align="left">Description</TableCell>
              <TableCell align="left">Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {productsData?.map((row: IProduct, index) => (
              <ProductRecord
                key={index}
                playlistId={+id}
                videoIndex={index}
                product={row}
              ></ProductRecord>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default SubRow;
