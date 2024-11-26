package com.coms4156.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) { SpringApplication.run(App.class, args); }

    @Override
    public void run(String... args) throws IOException {

        System.out.println("Fetching all food listings:");
        MainController mainController = new MainController(restTemplate);
        for (FoodListing f: mainController.getFoodListings()){
            System.out.println(f.getFoodType());
        }

        System.out.println("Fetching nearby food listings in 1 mile radius:");
        for (FoodListing f: mainController.getNearbyListings((float)40.7128, (float)-74.006, 1)){
            System.out.println(f.getFoodType());
        }

        System.out.println("Making a request for 2 fruit baskets and printing the request id:");
        ResponseEntity<FoodRequest> response = mainController.createFoodRequest(8, 17, 13, 2);
        System.out.println(response.getBody().getRequestId());

//        UserAuthentication userAuth = new UserAuthentication();
//        // Try to register a user
//        System.out.println("Registering a new user...");
//        JsonObject regResult = userAuth.registerUser("TEST_USER2", "TEST_PW");
//        System.out.println("Result: " + regResult);
//
//        // Try to authenticate a user
//        System.out.println("Authenticating users...");
//        // Should be ok
//        JsonObject authResult = userAuth.authenticateUser("TEST_USER2", "TEST_PW");
//        System.out.println("Result: " + authResult);
//        // Should fail
//        JsonObject authResult2 = userAuth.authenticateUser("TEST_USER2", "Talksjflka");
//        System.out.println("Result: " + authResult2);
    }

}
