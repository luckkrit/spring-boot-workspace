package com.k9.backend.shopee.dtos;

import lombok.Data;

@Data
public class CartProductDTO {
    private Long productId;
    private Long quantity;
}
