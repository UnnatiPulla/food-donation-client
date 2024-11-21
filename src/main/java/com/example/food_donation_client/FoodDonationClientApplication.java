package com.example.food_donation_client;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class FoodDonationClientApplication implements CommandLineRunner {

	@Autowired
	private FoodListingService foodListingService;

	public static void main(String[] args) {
		SpringApplication.run(FoodDonationClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
//		// Call the method in FoodListingService
//		System.out.println("Fetching all available food listings...");
//		foodListingService.getAllAvailableFoodListings().forEach(System.out::println);

		// Try to register a user
		System.out.println("Registering a new user...");
		UserAuthentication userAuth = new UserAuthentication();
		JsonObject regResult = userAuth.registerUser("TEST_USER2", "TEST_PW");
		System.out.println("Result: " + regResult);

		// Try to authenticate a user
		System.out.println("Authenticating users...");
		// Should be ok
		JsonObject authResult = userAuth.authenticateUser("TEST_USER2", "TEST_PW");
		System.out.println("Result: " + authResult);
		// Should fail
		JsonObject authResult2 = userAuth.authenticateUser("TEST_USER2", "Talksjflka");
		System.out.println("Result: " + authResult2);
	}
}
