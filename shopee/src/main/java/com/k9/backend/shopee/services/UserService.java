package com.k9.backend.shopee.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.k9.backend.shopee.dtos.user.AddressDTO;
import com.k9.backend.shopee.dtos.user.GeolocationDTO;
import com.k9.backend.shopee.dtos.user.NameDTO;
import com.k9.backend.shopee.dtos.user.UserDTO;
import com.k9.backend.shopee.models.User;
import com.k9.backend.shopee.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers(Optional<Integer> limit, Optional<String> sort) {
        List<User> users = userRepository.findAll(this.getPageable(limit, sort)).getContent();
        return users.stream().map(user -> this.getUserDTO(user)).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUser(Long userId) {
        var optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return Optional.of(this.getUserDTO(optionalUser.get()));
        } else {
            return Optional.ofNullable(null);
        }
    }

    private UserDTO getUserDTO(User user) {
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        var userDetail = user.getUserDetail();
        var address = user.getAddress();
        if (userDetail != null) {
            userDTO.setPhone(userDetail.getPhone());
            var nameDTO = new NameDTO();
            nameDTO.setFirstname(userDetail.getFirstname());
            nameDTO.setLastname(userDetail.getLastname());
            userDTO.setName(nameDTO);
        }
        if (address != null) {
            var addressDTO = new AddressDTO();
            addressDTO.setCity(address.getCity());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setNumber(address.getNumber());
            addressDTO.setZipcode(address.getZipcode());
            var geolocation = address.getLocation();
            if (geolocation != null) {
                var geolocationDTO = new GeolocationDTO();
                geolocationDTO.setLatitude(geolocation.getLatitude());
                geolocationDTO.setLongitude(geolocation.getLongtitude());
                addressDTO.setGeolocation(geolocationDTO);
            }
            userDTO.setAddress(addressDTO);
        }
        return userDTO;
    }

    private Pageable getPageable(Optional<Integer> optionalLimit, Optional<String> optionalSort) {
        var limit = optionalLimit.isPresent() ? optionalLimit.get() : (int) this.userRepository.count();
        var direction = optionalSort.isPresent() ? optionalSort.get().equals("asc") ? Direction.ASC : Direction.DESC
                : Direction.ASC;
        return PageRequest.of(0, limit, direction, "id");
    }
}
