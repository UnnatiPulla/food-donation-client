package com.example.food_donation_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodDonationClientApplication implements CommandLineRunner {

	@Autowired
	private FoodListingService foodListingService;

	public static void main(String[] args) {
		SpringApplication.run(FoodDonationClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Call the method in FoodListingService
		System.out.println("Fetching all available food listings...");
		foodListingService.getAllAvailableFoodListings().forEach(System.out::println);
	}
}
