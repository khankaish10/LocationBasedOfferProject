package com.kaish.offers_platform.controller;


import com.kaish.offers_platform.dto.StoreRequestDTO;
import com.kaish.offers_platform.dto.StoreResponseDTO;
import com.kaish.offers_platform.entity.Store;
import com.kaish.offers_platform.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public StoreResponseDTO create(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {
        return storeService.create(storeRequestDTO);
    }

    @GetMapping
    public List<Store> getStores(@RequestParam(required = false) String city) {
        if(city != null) {
            return storeService.getByCity(city);
        }
        return storeService.getAll();
    }


}
