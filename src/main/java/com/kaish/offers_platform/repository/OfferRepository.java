package com.kaish.offers_platform.repository;

import com.kaish.offers_platform.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {


    Page<Offer> findByStore_City(String city, Pageable pageable);

    Page<Offer> findByStore_id(Long storeId, Pageable pageable);

}
