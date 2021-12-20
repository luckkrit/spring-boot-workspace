package com.k9.backend.shopee.repository;

import java.util.Date;
import java.util.List;

import com.k9.backend.shopee.models.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    public Page<Cart> findAllByDateBetween(Pageable pageable, Date startdate, Date enddate);

    public Page<Cart> findAllByDateGreaterThanEqual(Pageable pageable, Date startdate);

    public Page<Cart> findAllByDateLessThanEqual(Pageable pageable, Date enddate);

    public List<Cart> findByUserId(Long userId);
}
