package com.k9.backend.shopee.controllers;

import java.util.List;
import java.util.Optional;

import com.k9.backend.shopee.dtos.user.AddUserDTO;
import com.k9.backend.shopee.dtos.user.UpdateUserDTO;
import com.k9.backend.shopee.dtos.user.UserDTO;
import com.k9.backend.shopee.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort) {
        return ResponseEntity.ok(this.userService.getAllUsers(limit, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        var optionalUserDTO = this.userService.getUser(id);
        if (optionalUserDTO.isPresent()) {
            return ResponseEntity.ok(optionalUserDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody AddUserDTO addUserDTO) {
        var optionalUserDTO = this.userService.addUser(addUserDTO);
        if (optionalUserDTO.isPresent()) {
            return new ResponseEntity<UserDTO>(optionalUserDTO.get(), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> putUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        var optionalUserDTO = this.userService.updateUser(id, updateUserDTO);
        if (optionalUserDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        var optionalUserDTO = this.userService.updateUser(id, updateUserDTO);
        if (optionalUserDTO.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
