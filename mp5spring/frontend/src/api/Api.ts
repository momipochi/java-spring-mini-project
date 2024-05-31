import { IPlaylist, IProduct } from "../interfaces/Interfaces";

export async function getPlaylists(): Promise<IPlaylist[]> {
  const response: Response = await fetch(
    "http://localhost:8080/playlist/getAllPlaylists"
  );
  const res = await response.json();
  if (!response.ok) {
    throw new Error("Error get playlists");
  }
  // console.log(response.status);
  return res;
}

export async function getProductsFromPlaylist(
  playlistId: number
): Promise<IProduct[]> {
  const response: Response = await fetch(
    `http://localhost:8080/playlist/getProductsFromPlaylist?listId=${playlistId}`
  );
  console.log("yea im here");
  console.log(response.status);
  if (response.status == 204) {
    return [];
  }
  if (!response.ok) {
    console.log("oof theres an error");
    throw new Error("Error get products from playlist");
  }
  const res = await response.json();

  console.log(res);
  return res;
}

export async function removeVideoFromPlaylist(
  playlistId: number,
  videoIndex: number
): Promise<boolean> {
  const url: string = `http://localhost:8080/playlist/removeVideoFromPlaylist?listId=${playlistId}\&videoIndex=${videoIndex}`;
  console.log(url);
  const response: Response = await fetch(url, { method: "DELETE" });
  console.log(response.status);
  // const res = await response.json();
  if (!response.ok) {
    
    return false;
  }
  // console.log(res);
  console.log(response.status);
  console.log(response.ok);
  return true;
}
