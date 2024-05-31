import {
  Button,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useEffect } from "react";
import {
  useMutation,
  UseMutationResult,
  useQuery,
  useQueryClient,
} from "react-query";
import { getProductsFromPlaylist, removeVideoFromPlaylist } from "../api/Api";
import { IProduct } from "../interfaces/Interfaces";

type RemoveParam = {
  playlistId: number;
  videoIndex: number;
  product: IProduct;
};

type RemoveParamMutate = {
  playlistId: number;
  videoIndex: number;
};
const ProductRecord = (props: RemoveParam) => {
  const queryClient = useQueryClient();
  const {
    data: productsData,
    isError: productsIsError,
    isLoading: productsLoading,
    error: productsError,
    refetch: refetchProducts,
  } = useQuery(
    "playlistRerender",
    async () => {
      return await getProductsFromPlaylist(props.playlistId);
    },
    {
      refetchOnWindowFocus: false,
      enabled: false,
    }
  );
  useEffect(() => {
    refetchProducts();
  }, []);
  const removeProductMutation: UseMutationResult<
    void,
    Error,
    RemoveParamMutate
  > = useMutation<void, Error, RemoveParamMutate>(
    async ({ playlistId, videoIndex }) => {
      await removeVideoFromPlaylist(playlistId, videoIndex);
      await queryClient.invalidateQueries("productsInPlaylist");
    },
    {
      onMutate: () => {
        console.log(queryClient.getQueryData("productsInPlaylist"));
      },
      onError: (error, edited, rollback) => {
        console.log(error.message);
      },
      onSettled: (data, error, variables, context) => {
        console.log(error?.message);
      },
      onSuccess(data, variables, context) {
        console.log(variables);
      },
    }
  );
  return (
    <TableRow
      key={props.product.id}
      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
    >
      <TableCell align="center" component="th" scope="props.product">
        {props.product.id}
      </TableCell>
      <TableCell align="left" component="th" scope="props.product">
        {props.product.productName}
      </TableCell>
      <TableCell align="left" component="th" scope="props.product">
        {props.product.productDescription}
      </TableCell>
      <TableCell align="left" component="th" scope="props.product">
        <Button
          variant="contained"
          color="error"
          onClick={(e) => {
            if (window.confirm("Delete from playlist?")) {
              console.log("walbadaldub dub");
              removeProductMutation.mutate({
                playlistId: props.playlistId,
                videoIndex: props.videoIndex,
              });
              e.persist();
              refetchProducts();
              console.log("im onclick");
              console.log(productsData);
            }
          }}
        >
          Delete
        </Button>
      </TableCell>
    </TableRow>
  );
};

export default ProductRecord;
