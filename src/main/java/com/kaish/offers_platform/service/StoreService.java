package com.kaish.offers_platform.service;


import com.kaish.offers_platform.dto.StoreRequestDTO;
import com.kaish.offers_platform.dto.StoreResponseDTO;
import com.kaish.offers_platform.entity.Store;
import com.kaish.offers_platform.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository  = storeRepository;
    }

    public StoreResponseDTO create(StoreRequestDTO storeRequestDTO) {

        Store store = new Store();
        store.setName(storeRequestDTO.getName());
        store.setAddress(storeRequestDTO.getAddress());
        store.setCity(storeRequestDTO.getCity());

        Store savedStore = storeRepository.save(store);

        StoreResponseDTO response = new StoreResponseDTO();
        response.setName(savedStore.getName());
        response.setCity(savedStore.getCity());
        response.setAddress(savedStore.getAddress());
        response.setId(savedStore.getId());
        return response;
    }

    public List<Store> getByCity(String city) {
        return storeRepository.findByCity(city);
    }

    public List<Store> getAll() {
        return storeRepository.findAll();
    }

}
