package com.k9.backend.shopee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDTO {
    private Long id;
    private String title;
    private Float price;
    private String category;
    private String description;
    private String image;
}
