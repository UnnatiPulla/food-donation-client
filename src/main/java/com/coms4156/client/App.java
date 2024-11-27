package com.coms4156.client;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        // Initialize Firebase. You need to have Default App Credentials by this point!
        // https://cloud.google.com/docs/authentication/provide-credentials-adc
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                                      .setCredentials(credentials)
                                      .setProjectId("food-sharing-service-438119")
                                      .build();
        FirebaseApp.initializeApp(options);
    }
}
