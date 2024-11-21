package com.coms4156.client;

import com.google.gson.JsonObject;
import org.apache.hc.core5.reactor.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.io.IOException;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) { SpringApplication.run(App.class, args); }

    @Override
    public void run(String... args) throws IOException {
        // UserAuthentication userAuth = new UserAuthentication();
        // // Try to register a user
        // System.out.println("Registering a new user...");
        // JsonObject regResult = userAuth.registerUser("TEST_USER2", "TEST_PW");
        // System.out.println("Result: " + regResult);

        // // Try to authenticate a user
        // System.out.println("Authenticating users...");
        // // Should be ok
        // JsonObject authResult = userAuth.authenticateUser("TEST_USER2", "TEST_PW");
        // System.out.println("Result: " + authResult);
        // // Should fail
        // JsonObject authResult2 = userAuth.authenticateUser("TEST_USER2", "Talksjflka");
        // System.out.println("Result: " + authResult2);
    }

}
