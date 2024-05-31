

type SubRowProp = {
  playlistId: number;
  opened: boolean;
};

export interface IPlaylist {
  id: number;
  playlistName: string;
  size: number;
  products: IProduct[];
}

export interface IProduct {
  id: number;
  productName: string;
  productDescription: string;
}
