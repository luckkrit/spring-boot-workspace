package com.k9.backend.shopee.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.k9.backend.shopee.dtos.user.AddUserDTO;
import com.k9.backend.shopee.dtos.user.AddressDTO;
import com.k9.backend.shopee.dtos.user.GeolocationDTO;
import com.k9.backend.shopee.dtos.user.NameDTO;
import com.k9.backend.shopee.dtos.user.UpdateUserDTO;
import com.k9.backend.shopee.dtos.user.UserDTO;
import com.k9.backend.shopee.models.Address;
import com.k9.backend.shopee.models.Geolocation;
import com.k9.backend.shopee.models.User;
import com.k9.backend.shopee.models.UserDetail;
import com.k9.backend.shopee.repository.AddressRepository;
import com.k9.backend.shopee.repository.GeolocationRepository;
import com.k9.backend.shopee.repository.UserDetailRepository;
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
    private final GeolocationRepository geolocationRepository;
    private final AddressRepository addressRepository;
    private final UserDetailRepository userDetailRepository;

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
                geolocationDTO.setLongtitude(geolocation.getLongtitude());
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

    public Optional<UserDTO> addUser(AddUserDTO addUserDTO) {
        var addressDTO = addUserDTO.getAddress();
        if (addressDTO == null) {
            return Optional.ofNullable(null);
        }
        var geolocationDTO = addressDTO.getGeolocation();
        if (geolocationDTO == null) {
            return Optional.ofNullable(null);
        }
        var nameDTO = addUserDTO.getName();
        if (nameDTO == null) {
            return Optional.ofNullable(null);
        }
        // before save
        var geolocation = new Geolocation();
        geolocation.setLatitude(geolocationDTO.getLatitude());
        geolocation.setLongtitude(geolocationDTO.getLongtitude());
        var saveGeolocation = this.geolocationRepository.save(geolocation);

        var address = new Address();
        address.setLocation(saveGeolocation);
        address.setCity(addressDTO.getCity());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setZipcode(addressDTO.getZipcode());
        var saveAddress = this.addressRepository.save(address);

        var userDetail = new UserDetail();
        userDetail.setFirstname(nameDTO.getFirstname());
        userDetail.setLastname(nameDTO.getLastname());
        userDetail.setPhone(addUserDTO.getPhone());
        var saveUserDetail = this.userDetailRepository.save(userDetail);

        var user = new User();
        user.setAddress(saveAddress);
        user.setEmail(addUserDTO.getEmail());
        user.setPassword(addUserDTO.getPassword());
        user.setUsername(addUserDTO.getUsername());
        user.setUserDetail(saveUserDetail);
        var saveUser = this.userRepository.save(user);
        // after save
        var userDTO = new UserDTO();
        userDTO.setName(nameDTO);
        userDTO.setAddress(addressDTO);
        userDTO.setId(saveUser.getId());
        userDTO.setEmail(saveUser.getEmail());
        userDTO.setUsername(saveUser.getUsername());
        userDTO.setPhone(saveUserDetail.getPhone());
        return Optional.of(userDTO);
    }

    public Optional<UserDTO> updateUser(Long userId, UpdateUserDTO updateUserDTO) {
        var addressDTO = updateUserDTO.getAddress();
        if (addressDTO == null) {
            return Optional.ofNullable(null);
        }
        var geolocationDTO = addressDTO.getGeolocation();
        if (geolocationDTO == null) {
            return Optional.ofNullable(null);
        }
        var nameDTO = updateUserDTO.getName();
        if (nameDTO == null) {
            return Optional.ofNullable(null);
        }
        // before save
        var optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.ofNullable(null);
        }
        var user = optionalUser.get();
        var address = user.getAddress();
        if (address == null) {
            address = new Address();
        }
        var geolocation = address.getLocation();
        if (geolocation == null) {
            geolocation = new Geolocation();
        }
        geolocation.setLatitude(geolocationDTO.getLatitude());
        geolocation.setLongtitude(geolocationDTO.getLongtitude());
        var saveGeolocation = this.geolocationRepository.save(geolocation);

        address.setLocation(saveGeolocation);
        address.setCity(addressDTO.getCity());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setZipcode(addressDTO.getZipcode());
        var saveAddress = this.addressRepository.save(address);

        var userDetail = user.getUserDetail();
        if (userDetail == null) {
            userDetail = new UserDetail();
        }
        userDetail.setFirstname(nameDTO.getFirstname());
        userDetail.setLastname(nameDTO.getLastname());
        userDetail.setPhone(updateUserDTO.getPhone());
        var saveUserDetail = this.userDetailRepository.save(userDetail);

        user.setAddress(saveAddress);
        user.setEmail(updateUserDTO.getEmail());
        user.setPassword(updateUserDTO.getPassword());
        user.setUsername(updateUserDTO.getUsername());
        user.setUserDetail(saveUserDetail);
        var saveUser = this.userRepository.save(user);
        // after save
        var userDTO = new UserDTO();
        userDTO.setName(nameDTO);
        userDTO.setAddress(addressDTO);
        userDTO.setId(saveUser.getId());
        userDTO.setEmail(saveUser.getEmail());
        userDTO.setUsername(saveUser.getUsername());
        userDTO.setPhone(saveUserDetail.getPhone());
        return Optional.of(userDTO);
    }
}
