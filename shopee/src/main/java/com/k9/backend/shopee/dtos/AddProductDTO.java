package com.k9.backend.shopee.dtos;

import lombok.Data;

@Data
public class AddProductDTO {
    private String title;
    private float price;
    private String description;
    private String image;
    private Long categoryId;
}
