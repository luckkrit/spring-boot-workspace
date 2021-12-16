package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.List;

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

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Transactional
    public List<Product> getProducts(Long categoryId) {
        var category = this.getCategory(categoryId);
        if (category == null)
            return new ArrayList<Product>();
        Hibernate.initialize(category.getProducts());
        return category.getProducts();
    }
}
