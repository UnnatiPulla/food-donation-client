package com.example.food_donation_client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FoodDonationClientApplication implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(FoodDonationClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Assign RestTemplate to the global variable.
		GlobalInfo.assignRestTemplate(restTemplate);

		// For testing while building the app:
		System.out.println("Fetching all available food listings...");
		FoodListingService foodListingService = new FoodListingService();
		List<FoodListing> foodListings = foodListingService.getAllAvailableFoodListings();

		if (foodListings.isEmpty()) {
			System.out.println("No food listings available.");
		} else {
			foodListings.forEach(listing -> System.out.println("Listing: " + listing));
		}
		System.out.println("Fetching all nearby food listings...");

		List<FoodListing> foodListings2 = foodListingService.getNearbyListings(8, 34.052f, -118.243f, 1);
		if (foodListings.isEmpty()) {
			System.out.println("No nearby food listings available.");
		} else {
			foodListings.forEach(listing -> System.out.println("Listing: " + listing));
		}


	}
}
