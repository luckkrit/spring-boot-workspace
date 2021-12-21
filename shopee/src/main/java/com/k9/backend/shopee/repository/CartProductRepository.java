package com.k9.backend.shopee.repository;

import com.k9.backend.shopee.models.CartProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
