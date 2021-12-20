package com.k9.backend.shopee.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.CartDTO;
import com.k9.backend.shopee.services.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CartDTO>> getAllUserCarts(@PathVariable Long id) {
        return ResponseEntity.ok(this.cartService.getAllCartsByUser(id));
    }
}
