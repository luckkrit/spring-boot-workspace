package com.k9.backend.shopee.controllers;

import com.k9.backend.shopee.dtos.user.AddUserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody AddUserDTO addUserDTO) {
        logger.info(addUserDTO.getName().getFirstname());
        return ResponseEntity.ok("ok");
    }
}
