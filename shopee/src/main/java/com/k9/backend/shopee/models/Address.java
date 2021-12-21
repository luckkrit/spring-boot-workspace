package com.k9.backend.shopee.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NotBlank
    private String zipcode;
    private Long number;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @Id
    @SequenceGenerator(name = "addressSeqGen", sequenceName = "addressSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSeqGen")
    private Long id;

    @OneToOne
    @JoinColumn
    private Geolocation location;

    @OneToOne(mappedBy = "address")
    private User user;

}
