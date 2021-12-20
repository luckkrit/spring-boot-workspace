package com.k9.backend.shopee.services;

import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.AddProductDTO;
import com.k9.backend.shopee.dtos.ProductDTO;
import com.k9.backend.shopee.dtos.UpdateProductDTO;
import com.k9.backend.shopee.models.Product;
import com.k9.backend.shopee.repository.CategoryRepository;
import com.k9.backend.shopee.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;

        private Pageable getPageable(Optional<Integer> optionalLimit, Optional<String> optionalSort) {
                var limit = optionalLimit.isPresent() ? optionalLimit.get() : (int) this.productRepository.count();
                var direction = optionalSort.isPresent()
                                ? optionalSort.get().equals("asc") ? Direction.ASC : Direction.DESC
                                : Direction.ASC;
                return PageRequest.of(0, limit, direction, "id");
        }

        public List<ProductDTO> getAllProducts(Optional<Integer> limit, Optional<String> sort) {
                var pageable = this.getPageable(limit, sort);
                return this.productRepository.findAll(pageable)
                                .map(product -> new ProductDTO(product.getId(), product.getTitle(),
                                                product.getPrice(),
                                                product.getCategory().getTitle(),
                                                product.getDescription(), product.getImage()))
                                .getContent();
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
                        product = this.productRepository.save(product);
                        var productDTO = new ProductDTO(product.getId(), product.getTitle(), product.getPrice(),
                                        optionalCategory.get().getTitle(), product.getDescription(),
                                        product.getImage());
                        return Optional.of(productDTO);
                } else {
                        return Optional.ofNullable(null);
                }
        }

        public Optional<ProductDTO> updateProduct(Long productId, UpdateProductDTO updateProductDTO) {
                var optionalProduct = this.productRepository.findById(productId);
                var optionalCategory = this.categoryRepository.findById(updateProductDTO.getCategoryId());
                if (optionalProduct.isPresent() && optionalCategory.isPresent()) {

                        // Update product
                        var category = optionalCategory.get();
                        var product = optionalProduct.get();
                        product.setTitle(updateProductDTO.getTitle());
                        product.setPrice(updateProductDTO.getPrice());
                        product.setDescription(updateProductDTO.getDescription());
                        product.setImage(updateProductDTO.getImage());
                        product.setCategory(category);
                        this.productRepository.save(product);

                        var productDTO = new ProductDTO(product.getId(), updateProductDTO.getTitle(),
                                        updateProductDTO.getPrice(),
                                        category.getTitle(), updateProductDTO.getDescription(),
                                        updateProductDTO.getImage());
                        return Optional.of(productDTO);
                } else {
                        return Optional.ofNullable(null);
                }
        }

        public void deleteProduct(Long productId) {
                var optionalProduct = this.productRepository.findById(productId);
                if (optionalProduct.isPresent()) {
                        this.productRepository.delete(optionalProduct.get());
                }
        }
}
