package com.kaish.offers_platform.dto;

import java.util.List;

public class DiscoverStoreDTO {

    private Long storeId;
    private String storeName;
    private String city;
    private List<DiscoverOfferDTO> offers;

    public DiscoverStoreDTO (Long storeId, String storeName, String city, List<DiscoverOfferDTO> offers) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.city = city;
        this.offers = offers;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCity() {
        return city;
    }

    public List<DiscoverOfferDTO> getOffers() {
        return offers;
    }
}
