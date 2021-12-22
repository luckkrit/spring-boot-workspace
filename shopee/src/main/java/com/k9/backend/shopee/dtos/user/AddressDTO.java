package com.k9.backend.shopee.dtos.user;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String street;
    private Long number;
    private String zipcode;
    private GeolocationDTO geolocation;
}
