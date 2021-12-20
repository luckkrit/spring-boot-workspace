package com.k9.backend.shopee.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    private Long userId;
    private Date date;
    private List<CartProductDTO> products;
}
