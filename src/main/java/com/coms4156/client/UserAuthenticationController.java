package com.coms4156.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;

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
    try {
		JsonObject authResult = userAuthentication.registerUser(username, password);
		if (!authResult.get("success").getAsBoolean()) {
			return ResponseEntity.status(400).body(authResult.toString());
        }
		JsonObject profileResponse = userAuthentication.createAccountProfile(
			8, "RECIPIENT", "0123456789", username);
        if (!profileResponse.get("success").getAsBoolean()) {
            return ResponseEntity.status(400).body(profileResponse.toString());
        }
        
		int accountId = profileResponse.get("accountId").getAsInt();

		System.out.println("accountId is " + accountId);

		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("users").document(username);

        Map<String, Object> data = new HashMap<>();
		data.put("accountId", accountId);
        ApiFuture<WriteResult> future = docRef.set(data);
        future.get();        
		
		authResult.addProperty("accountId", accountId);
        return ResponseEntity.ok(authResult.toString());
		} catch (IOException | InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
}


  @PostMapping("/authenticate")
  public ResponseEntity<String> login(@RequestParam String username,
                                      @RequestParam String password) {
    try {
        JsonObject authResult = userAuthentication.authenticateUser(username, password);
        if (!authResult.get("success").getAsBoolean()) {
            return ResponseEntity.status(400).body(authResult.toString());
        }
		Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get(); 
		// System.out.println("document.exists?" + document);

		if (document.exists()) {
			Long accountIdLong = document.getLong("accountId");
			// System.out.println(accountIdLong);
            if (accountIdLong != null) {
                int accountId = accountIdLong.intValue();
                authResult.addProperty("accountId", accountId);
                return ResponseEntity.ok(authResult.toString());
            } else {
                authResult.addProperty("success", false);
                authResult.addProperty("message", "Account ID not found for user.");
                return ResponseEntity.status(400).body(authResult.toString());
            }
        } else {
            authResult.addProperty("success", false);
            authResult.addProperty("message", "User not found in Firestore.");
            return ResponseEntity.status(400).body(authResult.toString());
        }
    } catch (IOException | InterruptedException | ExecutionException e) {
        return ResponseEntity.status(500).body("Server error: " + e.getMessage());
    }
  }

}
