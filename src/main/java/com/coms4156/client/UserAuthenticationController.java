package com.coms4156.client;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Controller
@RequestMapping("/login")
public class UserAuthenticationController {

  private final UserAuthentication userAuthentication;

  public UserAuthenticationController(UserAuthentication userAuthentication) {
    this.userAuthentication = userAuthentication;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestParam String username,
                                         @RequestParam String password) {
    // TODO: This is test code!! Implement this fully.
    {
      Firestore db = FirestoreClient.getFirestore();

      DocumentReference docRef = db.collection("users").document("alovelace");
      // Add document data  with id "alovelace" using a hashmap
      Map<String, Object> data = new HashMap<>();
      data.put("first", "Ada");
      data.put("last", "Lovelace");
      data.put("born", 1815);
      //asynchronously write data
      ApiFuture<WriteResult> result = docRef.set(data);
      try {
          System.out.println("Update time : " + result.get().getUpdateTime());
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
    }
    
    try {
      JsonObject result = userAuthentication.registerUser(username, password);
      if (result.get("success").getAsBoolean()) {
        return ResponseEntity.ok(result.toString()); // Convert JsonObject to String
      } else {
        return ResponseEntity.status(400).body(result.toString());
      }
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }


  @PostMapping("/authenticate")
  public ResponseEntity<String> login(@RequestParam String username,
                                      @RequestParam String password) {
    try {
      JsonObject result = userAuthentication.authenticateUser(username, password);
      if (result.get("success").getAsBoolean()) {
        return ResponseEntity.ok(result.toString()); // Convert JsonObject to String
      } else {
        return ResponseEntity.status(400).body(result.toString());
      }
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }
}
