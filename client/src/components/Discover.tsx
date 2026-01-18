import { useEffect, useState } from "react";
import api from "../api/api";
import type { DiscoverStore } from "../types/discover.ts";

function Discover() {
  const [city, setCity] = useState("Berlin");
  const [data, setData] = useState<DiscoverStore[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchDiscover();
  }, []);

  const fetchDiscover = async () => {
    try {
      setLoading(true);
      const res = await api.get<DiscoverStore[]>(
        `/offers/discover?city=${city}`,
      );
      console.log("res - :", res.data);
      setData(res.data);
    } catch (err) {
      console.error("Failed to fetch discover data", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Discover Offers</h2>

      <div style={{ marginBottom: "1rem" }}>
        <input
          value={city}
          onChange={(e) => setCity(e.target.value)}
          placeholder="Enter city"
        />
        <button onClick={fetchDiscover}>Search</button>
      </div>

      {loading && <p>Loading...</p>}

      {!loading &&
        data.map((store) => (
          <div key={store.storeId} style={{ marginBottom: "1rem" }}>
            <h3>
              {store.storeName} ({store.city})
            </h3>

            <ul>
              {store.offers.map((offer) => (
                <li key={offer.id}>
                  {offer.title} - {offer.discount}% off
                </li>
              ))}
            </ul>
          </div>
        ))}
    </div>
  );
}

export default Discover;
