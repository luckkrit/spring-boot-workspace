package com.k9.backend.shopee.repository;

import com.k9.backend.shopee.models.Geolocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocationRepository extends JpaRepository<Geolocation, Long> {

}
