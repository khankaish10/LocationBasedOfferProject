package com.kaish.offers_platform.dto;

public class DiscoverOfferDTO {

    private Long id;
    private String title;
    private double discount;

    public Long getId() {
        return id;
    }

    public DiscoverOfferDTO(Long id, String title, double discount) {
        this.id = id;
        this.title = title;
        this.discount = discount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
