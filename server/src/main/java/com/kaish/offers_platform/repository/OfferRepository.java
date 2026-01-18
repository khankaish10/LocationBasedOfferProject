package com.kaish.offers_platform.repository;

import com.kaish.offers_platform.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {


    Page<Offer> findByStore_City(String city, Pageable pageable);

    Page<Offer> findByStore_id(Long storeId, Pageable pageable);

    List<Offer> findByStore_city(String city);

    // search by keyword
    List<Offer> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String DescriptionKeyword);

    List<Offer> findByStore_CityIgnoreCaseAndValidFromLessThanEqualAndValidToGreaterThanEqual(String city, LocalDate validFrom, LocalDate validTo);
    List<Offer> findByValidFromLessThanEqualAndValidToGreaterThanEqual(LocalDate validFrom, LocalDate validTo);

}
