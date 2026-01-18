export interface DiscoverOffer {
  id: number;
  title: string;
  discount: number;
}

export interface DiscoverStore {
  storeId: number;
  storeName: string;
  city: string;
  offers: DiscoverOffer[];
}
