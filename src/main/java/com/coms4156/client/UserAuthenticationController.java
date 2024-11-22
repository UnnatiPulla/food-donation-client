package com.coms4156.client;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class UserAuthenticationController {

  private final UserAuthentication userAuthentication;

  public UserAuthenticationController(UserAuthentication userAuthentication) {
    this.userAuthentication = userAuthentication;
  }

  @GetMapping("")
  public String loginPage() {
    return "login"; // Serve the login.html file from templates
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestParam String username,
                                         @RequestParam String password) {
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
