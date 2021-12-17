package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.models.Category;
import com.k9.backend.shopee.models.Product;
import com.k9.backend.shopee.repository.CategoryRepository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private Optional<Category> getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Transactional
    public List<Product> getProducts(Long categoryId) {
        var optionalCategory = this.getCategory(categoryId);
        if (!optionalCategory.isPresent())
            return new ArrayList<Product>();
        var category = optionalCategory.get();
        if (!Hibernate.isInitialized(category.getProducts()))
            Hibernate.initialize(category.getProducts());
        return category.getProducts();
    }
}
