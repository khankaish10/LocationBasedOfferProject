package com.kaish.offers_platform.dto;

import jakarta.validation.constraints.NotBlank;

public class StoreRequestDTO {

    @NotBlank
    private String name;


    @NotBlank
    private String city;

    @NotBlank
    private String address;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public @NotBlank String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank String address) {
        this.address = address;
    }
}
