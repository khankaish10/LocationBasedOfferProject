package com.kaish.offers_platform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class OfferRequestDTO {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @Min(value = 1, message = "Discount must be at least 1")
    @Max(value = 100, message = "Discount cannot exceed 100")
    private int discount;

    private String description;

    @NotNull(message = "Valid from date is required")
    private LocalDate validFrom;

    @NotNull(message = "ValidTo date is required")
    private LocalDate validTo;

    @NotNull(message = "storeId is required")
    private Long storeId;

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(@NotNull LocalDate validTo) {
        this.validTo = validTo;
    }

    public @NotNull LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(@NotNull LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public @NotNull Long getStoreId() {
        return storeId;
    }

    public void setStoreId(@NotNull Long storeId) {
        this.storeId = storeId;
    }
}
