package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.CartDTO;
import com.k9.backend.shopee.dtos.CartProductDTO;
import com.k9.backend.shopee.models.Cart;
import com.k9.backend.shopee.models.CartProduct;
import com.k9.backend.shopee.repository.CartProductRepository;
import com.k9.backend.shopee.repository.CartRepository;
import com.k9.backend.shopee.repository.ProductRepository;
import com.k9.backend.shopee.repository.UserRepository;

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
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
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
            cartDTO.setCartProducts(cartProductsDTO);
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

    public Optional<CartDTO> addCart(CartDTO cartDTO) {
        var optionalUser = this.userRepository.findById(cartDTO.getUserId());
        if (optionalUser.isPresent()) {
            // before save
            var cart = new Cart();
            cart.setDate(cartDTO.getDate());
            cart.setUser(optionalUser.get());
            final Cart saveCart = this.cartRepository.save(cart);
            // after save
            cartDTO.setId(saveCart.getId());
            cartDTO.getCartProducts().forEach(cartProductDTO -> {
                var optionalProduct = this.productRepository.findById(cartProductDTO.getProductId());
                if (optionalProduct.isPresent()) {
                    var cartProduct = new CartProduct();
                    cartProduct.setCart(saveCart);
                    cartProduct.setProduct(optionalProduct.get());
                    cartProduct.setQuantity(cartProductDTO.getQuantity());
                    this.cartProductRepository.save(cartProduct);
                }
            });
            return Optional.of(cartDTO);
        } else {
            return Optional.ofNullable(null);
        }
    }

    public Optional<CartDTO> updateCart(Long cartId, CartDTO cartDTO) {
        var optionalCart = this.cartRepository.findById(cartId);
        var optionalUser = this.userRepository.findById(cartDTO.getUserId());
        if (optionalCart.isPresent() && optionalUser.isPresent()) {
            // before save
            var cart = optionalCart.get();
            cart.setDate(cartDTO.getDate());
            cart.setUser(optionalUser.get());
            final Cart saveCart = this.cartRepository.save(cart);
            // after save
            // delete cart that link to cart product table
            var oldCartProducts = cart.getCartProducts();
            oldCartProducts.forEach(cartProduct -> {
                this.cartProductRepository.delete(cartProduct);
            });
            cartDTO.setId(saveCart.getId());
            cartDTO.getCartProducts().forEach(cartProductDTO -> {
                var optionalProduct = this.productRepository.findById(cartProductDTO.getProductId());
                if (optionalProduct.isPresent()) {
                    var cartProduct = new CartProduct();
                    cartProduct.setCart(saveCart);
                    cartProduct.setProduct(optionalProduct.get());
                    cartProduct.setQuantity(cartProductDTO.getQuantity());
                    this.cartProductRepository.save(cartProduct);
                }
            });
            return Optional.of(cartDTO);
        } else {
            return Optional.ofNullable(null);
        }
    }
}
