package com.k9.backend.shopee.controllers;

import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dto.ProductDTO;
import com.k9.backend.shopee.services.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort) {
        return ResponseEntity.ok(this.productService.getAllProducts(limit, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getAllProducts(@PathVariable Long id) {
        var optionalProductDTO = productService.getProduct(id);
        if (optionalProductDTO.isPresent())
            return ResponseEntity.ok(optionalProductDTO.get());
        return ResponseEntity.notFound().build();

    }

}
