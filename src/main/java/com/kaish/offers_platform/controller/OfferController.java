package com.kaish.offers_platform.controller;


import com.kaish.offers_platform.dto.OfferResponseDTO;
import com.kaish.offers_platform.entity.Offer;
import com.kaish.offers_platform.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // create offer
    @PostMapping
    public OfferResponseDTO create(@Valid @RequestBody OfferResponseDTO offerResponseDTO) {
        return offerService.create(offerResponseDTO);
    }


    @GetMapping
    public Page<OfferResponseDTO> getByCity(@RequestParam(required = false) String city,
                                 @RequestParam(required = false) Long storeId,
                                 @RequestParam(defaultValue = "0")   int page,
                                 @RequestParam(defaultValue = "5")  int size,
                                 @RequestParam(defaultValue = "id")  String sortBy,
                                 @RequestParam(defaultValue = "asc")  String direction

    ) {

        return offerService.getOffers(city, storeId, page, size, sortBy, direction);
    }


}
