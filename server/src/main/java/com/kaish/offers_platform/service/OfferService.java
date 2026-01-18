package com.kaish.offers_platform.service;


import com.kaish.offers_platform.dto.DiscoverOfferDTO;
import com.kaish.offers_platform.dto.DiscoverStoreDTO;
import com.kaish.offers_platform.dto.OfferResponseDTO;
import com.kaish.offers_platform.entity.Offer;
import com.kaish.offers_platform.entity.Store;
import com.kaish.offers_platform.repository.OfferRepository;
import com.kaish.offers_platform.repository.StoreRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        OfferResponseDTO response = new OfferResponseDTO(
        saved.getId(),
        saved.getTitle(),
        saved.getDescription(),
        saved.getDiscount(),
        saved.getValidFrom(),
        saved.getValidTo(),
        saved.getStore().getId(),
        saved.getStore().getName(),
        saved.getStore().getCity()
);
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

        if(city != null) {
            offerspage = offerRepository.findByStore_City(city, pageable);
        } else if(storeId != null){
            offerspage = offerRepository.findByStore_id(storeId, pageable);
        } else {
            offerspage = offerRepository.findAll(pageable);
        }

        return offerspage.map(o -> new OfferResponseDTO(
                    o.getId(),
                    o.getTitle(),
                    o.getDescription(),
                    o.getDiscount(),
                    o.getValidFrom(),
                    o.getValidTo(),
                    o.getStore().getId(),
                    o.getStore().getName(),
                    o.getStore().getCity()
        ));
    }

    public List<DiscoverStoreDTO> discoverOffers( String city, int limit) {

        List<Offer> offer = offerRepository.findByStore_city(city);

        Map<Store, List<Offer>> groupedByStore = offer.stream().collect(Collectors.groupingBy(Offer::getStore));

        List<DiscoverStoreDTO> result = new ArrayList<>();

        for(Map.Entry<Store, List<Offer>> entry : groupedByStore.entrySet()) {
            Store store = entry.getKey();
            List<Offer> storeOffers = entry.getValue();

            List<DiscoverOfferDTO> topOffers =  storeOffers
                    .stream().
                    sorted((a, b) -> Double.compare(b.getDiscount(), a.getDiscount()))
                    .limit(limit)
                    .map(o -> new DiscoverOfferDTO(
                            o.getId(),
                            o.getTitle(),
                            o.getDiscount()
                    ))
                    .toList();

            result.add(new DiscoverStoreDTO(
                    store.getId(),
                    store.getName(),
                    store.getCity(),
                    topOffers
            ));
        }

        return result;

//        Map<Store, List<Offer>> groupedByStore = new HashMap<>();
//        for(Offer x: offer) {
//            Store store = x.getStore();
//            if(!groupedByStore.containsKey(store)) {
//                groupedByStore.put(store, new ArrayList<>());
//            }
//
//            groupedByStore.get(store).add(x);
//
//        }
//            return result;
    }

    public List<OfferResponseDTO> searchOffers(String keyword) {
        List<Offer> offers = offerRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);

        List<OfferResponseDTO> result = offers.stream().map(o -> new OfferResponseDTO(
                o.getId(),
                o.getTitle(),
                o.getDescription(),
                o.getDiscount(),
                o.getValidFrom(),
                o.getValidTo(),
                o.getStore().getId(),
                o.getStore().getName(),
                o.getStore().getCity()
        )).toList();
        return result;
    }

    public List<OfferResponseDTO> getActiveOffers(String city) {

        LocalDate today = LocalDate.now();
        List<Offer> offers;
        if(city != null) {
            offers = offerRepository.findByStore_CityIgnoreCaseAndValidFromLessThanEqualAndValidToGreaterThanEqual(city, today, today);
        } else {
            offers = offerRepository.findByValidFromLessThanEqualAndValidToGreaterThanEqual(today, today);
        }
        return offers.stream().map(o -> new OfferResponseDTO(
                o.getId(),
                o.getTitle(),
                o.getDescription(),
                o.getDiscount(),
                o.getValidFrom(),
                o.getValidTo(),
                o.getStore().getId(),
                o.getStore().getName(),
                o.getStore().getCity()
        )).toList();
    }
}
