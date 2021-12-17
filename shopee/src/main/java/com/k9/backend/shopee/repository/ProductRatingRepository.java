package com.k9.backend.shopee.repository;

import com.k9.backend.shopee.models.ProductRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

}
