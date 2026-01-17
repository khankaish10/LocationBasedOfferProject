package com.kaish.offers_platform.repository;

import com.kaish.offers_platform.entity.Store;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {


    List<Store> findByCity(String city);

}
