package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.k9.backend.shopee.dtos.AddProductDTO;
import com.k9.backend.shopee.dtos.ProductDTO;
import com.k9.backend.shopee.models.Product;
import com.k9.backend.shopee.repository.CategoryRepository;
import com.k9.backend.shopee.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;

        public List<ProductDTO> getAllProducts(Optional<Integer> limit, Optional<String> sort) {
                if (limit.isEmpty() && sort.isEmpty())
                        return this.productRepository.findAll().stream()
                                        .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                        product.getPrice(),
                                                        product.getCategory().getTitle(), product.getDescription(),
                                                        product.getImage()))
                                        .collect(Collectors.toList());
                else if (limit.isPresent() && sort.isPresent()) {
                        if (sort.get().equals("asc")) {
                                return this.productRepository
                                                .findAll(PageRequest.of(0, limit.get(),
                                                                Sort.by(Sort.Direction.ASC, "id")))
                                                .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                                product.getPrice(),
                                                                product.getCategory().getTitle(),
                                                                product.getDescription(), product.getImage()))
                                                .getContent();
                        } else {
                                return this.productRepository
                                                .findAll(PageRequest.of(0, limit.get(),
                                                                Sort.by(Sort.Direction.DESC, "id")))
                                                .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                                product.getPrice(),
                                                                product.getCategory().getTitle(),
                                                                product.getDescription(), product.getImage()))
                                                .getContent();

                        }
                } else if (limit.isPresent() && sort.isEmpty()) {
                        return this.productRepository.findAll(PageRequest.of(0, limit.get()))
                                        .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                        product.getPrice(),
                                                        product.getCategory().getTitle(), product.getDescription(),
                                                        product.getImage()))
                                        .getContent();

                } else {
                        if (sort.get().equals("asc"))
                                return this.productRepository
                                                .findAll(
                                                                PageRequest.of(0, (int) this.productRepository.count(),
                                                                                Sort.by(Sort.Direction.ASC, "id")))
                                                .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                                product.getPrice(),
                                                                product.getCategory().getTitle(),
                                                                product.getDescription(), product.getImage()))
                                                .getContent();
                        else
                                return this.productRepository
                                                .findAll(
                                                                PageRequest.of(0, (int) this.productRepository.count(),
                                                                                Sort.by(Sort.Direction.DESC, "id")))
                                                .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                                product.getPrice(),
                                                                product.getCategory().getTitle(),
                                                                product.getDescription(), product.getImage()))
                                                .getContent();

                }
        }

        public Optional<ProductDTO> getProduct(Long productId) {
                var optionalProduct = this.productRepository.findById(productId);
                if (optionalProduct.isPresent()) {
                        var product = optionalProduct.get();
                        var productDTO = new ProductDTO(product.getId(), product.getTitle(), product.getPrice(),
                                        product.getCategory().getTitle(), product.getDescription(), product.getImage());
                        return Optional.of(productDTO);
                } else
                        return Optional.empty();
        }

        public Page<ProductDTO> getProductsByLimit(int limit) {
                return this.productRepository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "id")))
                                .map(product -> new ProductDTO(product.getId(), product.getTitle(), product.getPrice(),
                                                product.getCategory().getTitle(), product.getDescription(),
                                                product.getImage()));
        }

        public Optional<ProductDTO> addProduct(AddProductDTO addProductDTO) {
                var optionalCategory = this.categoryRepository.findById(addProductDTO.getCategoryId());
                if (optionalCategory.isPresent()) {
                        var product = new Product();
                        product.setId(0L);
                        product.setTitle(addProductDTO.getTitle());
                        product.setCategory(optionalCategory.get());
                        product.setDescription(addProductDTO.getDescription());
                        product.setPrice(addProductDTO.getPrice());
                        product.setImage(addProductDTO.getImage());
                        this.productRepository.save(product);
                        var productDTO = new ProductDTO(product.getId(), product.getTitle(), product.getPrice(),
                                        optionalCategory.get().getTitle(), product.getDescription(),
                                        product.getImage());
                        return Optional.of(productDTO);
                } else {
                        return Optional.ofNullable(null);
                }
        }
}
