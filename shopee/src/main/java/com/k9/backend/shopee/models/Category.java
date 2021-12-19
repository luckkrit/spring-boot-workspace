package com.k9.backend.shopee.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @Id
    @SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 5, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categorySeqGen")
    private Long id;

    @NotBlank
    private String title;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
