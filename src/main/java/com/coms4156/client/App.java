package com.coms4156.client;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.UUID;
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

        UserAuthentication userAuth = new UserAuthentication();
        // Try to register a user
        System.out.println("Registering a new user...");
        // Should fail: user already exists
        JsonObject regResult = userAuth.registerUser("TEST_USER2", "TEST_PW");
        System.out.println("Result: " + regResult);
        // Should be ok
        JsonObject regResult2 = userAuth.registerUser(UUID.randomUUID().toString(), "maowewnuno");
        System.out.println("Result: " + regResult2);
        // Should fail: username cannot be empty
        JsonObject regResult3 = userAuth.registerUser("", "!!!!!");
        System.out.println("Result: " + regResult3);


        // Try to authenticate a user
        System.out.println("Authenticating users...");
        // Should be ok
        JsonObject authResult = userAuth.authenticateUser("TEST_USER2", "TEST_PW");
        System.out.println("Result: " + authResult);
        // Should fail: user doesn't exist
        JsonObject authResult2 = userAuth.authenticateUser("wrong-user!", "blah");
        System.out.println("Result: " + authResult2);
        // Should fail: wrong pass word
        JsonObject authResult3 = userAuth.authenticateUser("TEST_USER2", "Talksjflka");
        System.out.println("Result: " + authResult3);
        // Authenticating user that's already authenticated: ok!
        JsonObject authResult4 = userAuth.authenticateUser("TEST_USER2", "TEST_PW");
        System.out.println("Result: " + authResult4);
    }

}
