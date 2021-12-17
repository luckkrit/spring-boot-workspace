package com.k9.backend.shopee.services;

import java.util.Optional;

import com.k9.backend.shopee.models.Cart;
import com.k9.backend.shopee.models.User;
import com.k9.backend.shopee.models.UserDetail;
import com.k9.backend.shopee.repository.CartRepository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private Optional<Cart> getCart(Long cartId) {
        return this.cartRepository.findById(cartId);
    }

    @Transactional
    private Optional<User> getUser(Optional<Cart> optionalCart) {
        if (optionalCart.isPresent()) {
            if (!Hibernate.isInitialized(optionalCart.get().getUser()))
                Hibernate.initialize(optionalCart.get().getUser());
            return Optional.ofNullable(optionalCart.get().getUser());
        } else
            return Optional.ofNullable(null);
    }

    @Transactional
    public UserDetail getUserDetail(Long cartId) {
        var optionalUser = this.getUser(this.getCart(cartId));
        if (optionalUser.isPresent()) {
            if (!Hibernate.isInitialized(optionalUser.get().getUserDetail()))
                Hibernate.initialize(optionalUser.get().getUserDetail());
            return optionalUser.get().getUserDetail();
        } else {
            return null;
        }
    }
}
