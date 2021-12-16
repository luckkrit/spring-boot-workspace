package com.k9.backend.shopee;

import com.k9.backend.shopee.models.Address;
import com.k9.backend.shopee.models.Category;
import com.k9.backend.shopee.models.User;
import com.k9.backend.shopee.repository.AddressRepository;
import com.k9.backend.shopee.repository.CategoryRepository;
import com.k9.backend.shopee.repository.GeolocationRepository;
import com.k9.backend.shopee.repository.ProductRepository;
import com.k9.backend.shopee.repository.UserDetailRepository;
import com.k9.backend.shopee.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopeeApplication.class, args);
	}

	@Bean
	public CommandLineRunner showProductAndCategoryData(ProductRepository productRepository,
			CategoryRepository categoryRepository) {
		return (args -> {
			System.out.println(productRepository.count() + " " + categoryRepository.count());
			categoryRepository.findById(3L).ifPresent((category -> this.showProduct(category)));
		});
	}

	public void showProduct(Category category) {

		category.getProducts().forEach((product -> {
			System.out.println(product.getTitle());
		}));
	}

	@Bean
	public CommandLineRunner showAddressAndGeolocationData(AddressRepository addressRepository,
			GeolocationRepository geolocationRepository) {
		return (args -> {
			System.out.println(addressRepository.count() + " " + geolocationRepository.count());
			addressRepository.findById(3L).ifPresent((address -> this.showAddress(address)));
		});
	}

	public void showAddress(Address address) {
		System.out.println(address.getCity() + " " + address.getLocation().getLatitude() + " "
				+ address.getLocation().getLongtitude());
	}

	@Bean
	public CommandLineRunner showUserAndUserDetailsData(UserRepository userRepository,
			UserDetailRepository userDetailRepository) {
		return (args -> {
			userRepository.findById(1L).ifPresent((user -> this.showUserDetails(user)));
		});
	}

	private void showUserDetails(User user) {
		System.out.println(user.getUserDetail().getFirstname());
	}
}
