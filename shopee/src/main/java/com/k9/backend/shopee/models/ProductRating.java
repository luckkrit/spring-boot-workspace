package com.k9.backend.shopee.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRating {

    private Float rate;
    private Long count;
    @Id
    @SequenceGenerator(name = "productRatingSeqGen", sequenceName = "productRatingSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productRatingSeqGen")
    private Long id;

    @OneToOne
    @JoinColumn
    private Product product;
}
