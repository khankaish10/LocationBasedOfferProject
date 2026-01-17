package com.kaish.offers_platform.service;


import com.kaish.offers_platform.dto.OfferResponseDTO;
import com.kaish.offers_platform.entity.Offer;
import com.kaish.offers_platform.entity.Store;
import com.kaish.offers_platform.repository.OfferRepository;
import com.kaish.offers_platform.repository.StoreRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final StoreRepository storeRepository;

    public OfferService(OfferRepository offerRepository,
                        StoreRepository storeRepository) {
        this.offerRepository = offerRepository;
        this.storeRepository = storeRepository;
    }

    public OfferResponseDTO create(OfferResponseDTO offerResponseDTO) {
        Store store = storeRepository.findById(offerResponseDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id " + offerResponseDTO.getStoreId()));

        Offer offer = new Offer();
        offer.setId(offerResponseDTO.getId());
        offer.setTitle(offerResponseDTO.getTitle());
        offer.setDescription(offerResponseDTO.getDescription());
        offer.setDiscount(offerResponseDTO.getDiscount());
        offer.setValidFrom(offerResponseDTO.getValidFrom());
        offer.setValidTo(offerResponseDTO.getValidTo());
        offer.setStore(store);

        Offer saved = offerRepository.save(offer);

        OfferResponseDTO response = new OfferResponseDTO();
        response.setId(saved.getId());
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setDiscount(saved.getDiscount());
        response.setValidFrom(saved.getValidFrom());
        response.setValidTo(saved.getValidTo());
        response.setStoreId(saved.getStore().getId());
        response.setStoreName(saved.getStore().getName());
        response.setCity(saved.getStore().getCity());

        return response;
    }

    public Page<OfferResponseDTO> getOffers( String city,
                                    Long storeId,
                                    int page,
                                    int size,
                                    String sortBy,
                                    String direction
    ) {

        // 1. sort, direction
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // 2. page, size
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. filter
        Page<Offer> offerspage ;
//        List<OfferResponseDTO> dtoList = new ArrayList<>();

        if(city != null) {
            offerspage = offerRepository.findByStore_City(city, pageable);
        } else if(storeId != null){
            offerspage = offerRepository.findByStore_id(storeId, pageable);
        } else {
            offerspage = offerRepository.findAll(pageable);
        }

        return offerspage.map(offer -> {
            OfferResponseDTO dto = new OfferResponseDTO();
            dto.setId(offer.getId());
            dto.setTitle(offer.getTitle());
            dto.setDescription(offer.getDescription());
            dto.setDiscount(offer.getDiscount());
            dto.setValidFrom(offer.getValidFrom());
            dto.setValidTo(offer.getValidTo());
            dto.setStoreId(offer.getStore().getId());
            dto.setStoreName(offer.getStore().getName());
            dto.setCity(offer.getStore().getCity());

            return dto;
        });
//        for(Offer offer : offerspage.getContent()) {
//            OfferResponseDTO dto = new OfferResponseDTO();
//            dto.setId(offer.getId());
//            dto.setTitle(offer.getTitle());
//            dto.setDescription(offer.getDescription());
//            dto.setDiscount(offer.getDiscount());
//            dto.setValidFrom(offer.getValidFrom());
//            dto.setValidTo(offer.getValidTo());
//            dto.setStoreId(offer.getStore().getId());
//            dto.setStoreName(offer.getStore().getName());
//            dto.setCity(offer.getStore().getCity());
//
//            dtoList.add(dto);
//        }
//
//        return new PageImpl<>(dtoList, pageable, offerspage.getTotalElements());

    }

}
