package com.k9.backend.shopee.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GeolocationDTO {
    @JsonProperty("lat")
    private Float latitude;
    @JsonProperty("long")
    private Float longtitude;
}
