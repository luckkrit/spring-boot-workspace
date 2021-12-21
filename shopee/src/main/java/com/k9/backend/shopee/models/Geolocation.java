package com.k9.backend.shopee.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "geolocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geolocation {

    @Id
    @SequenceGenerator(name = "geolocationSeqGen", sequenceName = "geolocationSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geolocationSeqGen")
    private Long id;
    private Float latitude;
    private Float longtitude;

    @OneToOne(mappedBy = "location")
    private Address address;

}
