package com.k9.backend.shopee.controllers;

import java.util.List;

import com.k9.backend.shopee.dtos.CategoryDTO;
import com.k9.backend.shopee.services.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        var optionalCategory = this.categoryService.getCategory(id);
        if (optionalCategory.isPresent()) {
            var category = optionalCategory.get();
            return ResponseEntity.ok(new CategoryDTO(category.getId(), category.getTitle()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
