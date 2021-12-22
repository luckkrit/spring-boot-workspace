package com.k9.backend.shopee.dtos.user;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String email;
    private String username;
    private String password;
    private NameDTO name;
    private AddressDTO address;
    private String phone;

}
