package com.k9.backend.shopee.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.CartDTO;
import com.k9.backend.shopee.services.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam("startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> startdate,
            @RequestParam("enddate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> enddate) {
        return ResponseEntity.ok(this.cartService.getAllCarts(limit, sort, startdate, enddate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        var optionalCartDTO = this.cartService.getCart(id);
        if (optionalCartDTO.isPresent()) {
            return ResponseEntity.ok(optionalCartDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CartDTO>> getAllUserCarts(@PathVariable Long id) {
        return ResponseEntity.ok(this.cartService.getAllCartsByUser(id));
    }

    @PostMapping
    public ResponseEntity<CartDTO> addCart(@RequestBody CartDTO cartDTO) {
        var optionalCartDTO = this.cartService.addCart(cartDTO);
        if (optionalCartDTO.isPresent()) {
            return new ResponseEntity<>(optionalCartDTO.get(), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> putCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        var optionalCartDTO = this.cartService.updateCart(id, cartDTO);
        if (optionalCartDTO.isPresent()) {
            return new ResponseEntity<>(optionalCartDTO.get(), HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartDTO> patchCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        var optionalCartDTO = this.cartService.updateCart(id, cartDTO);
        if (optionalCartDTO.isPresent()) {
            return new ResponseEntity<>(optionalCartDTO.get(), HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartDTO> deleteCart(@PathVariable Long id) {
        var optionalCartDTO = this.cartService.deleteCart(id);
        if (optionalCartDTO.isPresent()) {
            return ResponseEntity.ok(optionalCartDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
