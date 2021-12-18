package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.k9.backend.shopee.dto.ProductDTO;
import com.k9.backend.shopee.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return this.productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getPrice(),
                        product.getCategory().getTitle(), product.getDescription(), product.getImage()))
                .collect(Collectors.toList());
    }
}
