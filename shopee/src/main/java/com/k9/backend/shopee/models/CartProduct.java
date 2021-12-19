package com.k9.backend.shopee.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;

@Entity
@Table(name = "cart_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {

    private Long quantity;
    @Id
    @SequenceGenerator(name = "cartProductSeqGen", sequenceName = "cartProductSeq", initialValue = 15, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cartProductSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
