package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.CartDTO;
import com.k9.backend.shopee.dtos.CartProductDTO;
import com.k9.backend.shopee.models.Cart;
import com.k9.backend.shopee.repository.CartRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    Logger logger = LoggerFactory.getLogger(CartService.class);

    public List<CartDTO> getAllCarts(Optional<Integer> limit, Optional<String> sort, Optional<Date> startdate,
            Optional<Date> enddate) {
        var pageable = this.getPageable(limit, sort);
        return this.getAllCartsDTO(pageable, startdate, enddate);
    }

    public List<CartDTO> getAllCartsByUser(Long userId) {
        var carts = this.cartRepository.findByUserId(userId);
        return this.getAllCartProductsDTO(carts);
    }

    private Pageable getPageable(Optional<Integer> optionalLimit, Optional<String> optionalSort) {
        var limit = optionalLimit.isPresent() ? optionalLimit.get() : (int) this.cartRepository.count();
        var direction = optionalSort.isPresent() ? optionalSort.get().equals("asc") ? Direction.ASC : Direction.DESC
                : Direction.ASC;
        return PageRequest.of(0, limit, direction, "id");
    }

    private List<CartDTO> getAllCartsDTO(Pageable pageable, Optional<Date> startdate, Optional<Date> enddate) {
        if (startdate.isPresent() && enddate.isPresent()) {
            var carts = this.cartRepository.findAllByDateBetween(pageable, startdate.get(), enddate.get()).getContent();
            return this.getAllCartProductsDTO(carts);
        } else if (startdate.isPresent()) {
            var carts = this.cartRepository.findAllByDateGreaterThanEqual(pageable, startdate.get()).getContent();
            return this.getAllCartProductsDTO(carts);
        } else if (enddate.isPresent()) {
            var carts = this.cartRepository.findAllByDateLessThanEqual(pageable, enddate.get()).getContent();
            return this.getAllCartProductsDTO(carts);
        } else {
            var carts = this.cartRepository.findAll(pageable).getContent();
            return this.getAllCartProductsDTO(carts);
        }
    }

    private List<CartDTO> getAllCartProductsDTO(List<Cart> carts) {
        var cartsDTO = new ArrayList<CartDTO>();
        carts.forEach(cart -> {
            var cartProducts = cart.getCartProducts();
            var cartDTO = new CartDTO();
            var cartProductsDTO = new ArrayList<CartProductDTO>();
            cartDTO.setProducts(cartProductsDTO);
            cartDTO.setDate(cart.getDate());
            cartDTO.setId(cart.getId());
            var user = cart.getUser();
            if (user != null) {
                cartDTO.setUserId(user.getId());
            }
            cartsDTO.add(cartDTO);
            cartProducts.forEach(cartProduct -> {
                var product = cartProduct.getProduct();
                var cartProductDTO = new CartProductDTO();
                if (product != null) {
                    cartProductDTO.setProductId(product.getId());
                    cartProductDTO.setQuantity(cartProduct.getQuantity());
                    cartProductsDTO.add(cartProductDTO);
                }
            });

        });
        return cartsDTO;
    }

}
