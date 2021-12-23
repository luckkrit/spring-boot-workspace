package com.k9.backend.shopee.controllers;

import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.AddProductDTO;
import com.k9.backend.shopee.dtos.ProductDTO;
import com.k9.backend.shopee.dtos.UpdateProductDTO;
import com.k9.backend.shopee.services.ProductService;

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

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductDTO addProductDTO) {
        var optionalProductDTO = productService.addProduct(addProductDTO);
        if (optionalProductDTO.isPresent()) {
            return new ResponseEntity<>(optionalProductDTO.get(), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> putProduct(@PathVariable Long id,
            @RequestBody UpdateProductDTO updateProductDTO) {
        var optionalProductDTO = productService.updateProduct(id, updateProductDTO);
        if (optionalProductDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable Long id,
            @RequestBody UpdateProductDTO updateProductDTO) {
        var optionalProductDTO = productService.updateProduct(id, updateProductDTO);
        if (optionalProductDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        var optionalProductDTO = this.productService.deleteProduct(id);
        if (optionalProductDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {

            return ResponseEntity.noContent().build();
        }
    }

}
