package com.k9.backend.shopee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.k9.backend.shopee.dtos.CategoryDTO;
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

    public Optional<Category> getCategory(Long categoryId) {
        return this.categoryRepository.findById(categoryId);
    }

    public List<CategoryDTO> getAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map((category -> new CategoryDTO(category.getId(), category.getTitle()))).collect(Collectors.toList());
    }
}
