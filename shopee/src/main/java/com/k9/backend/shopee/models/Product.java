package com.k9.backend.shopee.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @SequenceGenerator(name = "productSeqGen", sequenceName = "productSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeqGen")
    private Long id;

    @NotBlank
    private String title;

    private Float price;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String image;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToOne(mappedBy = "product")
    private ProductRating productRating;

    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts;
}
