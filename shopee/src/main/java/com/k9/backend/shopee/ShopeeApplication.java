package com.k9.backend.shopee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopeeApplication {

	Logger logger = LoggerFactory.getLogger(ShopeeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ShopeeApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner getAllProducts(ProductService productService) {
	// return (args -> {
	// productService.getAllProducts().stream()
	// .forEach(product -> logger.info(product.getId().toString() + ", " +
	// product.getTitle() + ", "
	// + product.getDescription() + ", " + product.getCategory() + ", " +
	// product.getPrice() + ", "
	// + product.getImage()));
	// });
	// }

	// @Bean
	// public CommandLineRunner showProductAndCategoryData(CategoryService
	// categoryService) {
	// return (args -> {
	// List<Product> products = categoryService.getProducts(3L);
	// products.stream().forEach(product -> logger.info(product.getTitle()));
	// });
	// }

	// @Bean
	// public CommandLineRunner showAddressAndGeolocationData(AddressRepository
	// addressRepository,
	// GeolocationRepository geolocationRepository) {
	// return (args -> {
	// logger.info(addressRepository.count() + " " + geolocationRepository.count());
	// addressRepository.findById(3L).ifPresent((address ->
	// this.showAddress(address)));
	// });
	// }

	// public void showAddress(Address address) {
	// logger.info(address.getCity() + " " + address.getLocation().getLatitude() + "
	// "
	// + address.getLocation().getLongtitude());
	// }

	// @Bean
	// public CommandLineRunner showUserAndUserDetailsData(UserRepository
	// userRepository,
	// UserDetailRepository userDetailRepository) {
	// return (args -> {
	// userRepository.findById(1L).ifPresent((user -> this.showUserDetails(user)));
	// });
	// }

	// private void showUserDetails(User user) {
	// logger.info(user.getUserDetail().getFirstname());
	// }

	// @Bean
	// public CommandLineRunner showProductRating(ProductRepository
	// productRepository) {
	// return (args -> {
	// productRepository.findById(3L)
	// .ifPresent((product ->
	// logger.info(product.getProductRating().getRate().toString())));

	// });
	// }

	// @Bean
	// public CommandLineRunner showCart(CartService service) {
	// return (args -> {
	// logger.info(service.getUserDetail(3L).getFirstname());
	// });
	// }

	// @Bean
	// public CommandLineRunner showCartProducts(CartService service) {
	// return (args -> {
	// service.getProducts(4L).stream().forEach(product ->
	// logger.info(product.getTitle()));
	// });
	// }
}
