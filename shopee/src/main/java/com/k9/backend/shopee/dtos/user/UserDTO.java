package com.k9.backend.shopee.dtos.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private NameDTO name;
    private AddressDTO address;
    private String phone;
}
