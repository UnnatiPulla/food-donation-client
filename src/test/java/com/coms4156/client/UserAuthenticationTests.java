package com.coms4156.client;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserAuthenticationTests {

  private UserAuthentication userAuth;
  private HttpURLConnection mockConnection;

  @BeforeEach
  void setup() {
    userAuth = spy(new UserAuthentication()); // Use spy to partially mock the class
    mockConnection = mock(HttpURLConnection.class); // Mock HTTP connection
  }

  @Test
  void testRegisterUserSuccess() throws Exception {
    // Mock successful registration response
    doReturn(mockConnection).when(userAuth).registerUserWithFirebase(anyString(), anyString());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

    JsonObject mockResponse = new JsonObject();
    mockResponse.addProperty("responseCode", HttpURLConnection.HTTP_OK);
    mockResponse.add("responseBody", new JsonObject());

    doReturn(mockResponse).when(userAuth).getFirebaseResponse(mockConnection);

    JsonObject result = userAuth.registerUser("testUser", "testPassword");

    assertEquals(true, result.get("success").getAsBoolean());
    assertEquals("User successfully registered!", result.get("message").getAsString());
  }
  @Test
  void testAuthenticateUserFailure() throws Exception {
    // Mock failed authentication response
    doReturn(mockConnection).when(userAuth).authenticateUserWithFirebase(anyString(), anyString());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);

    JsonObject errorResponse = new JsonObject();
    JsonObject responseBody = new JsonObject();
    JsonObject errorDetails = new JsonObject();
    errorDetails.addProperty("message", "Invalid username or password.");
    responseBody.add("error", errorDetails);
    errorResponse.addProperty("responseCode", HttpURLConnection.HTTP_UNAUTHORIZED);
    errorResponse.add("responseBody", responseBody);

    doReturn(errorResponse).when(userAuth).getFirebaseResponse(mockConnection);

    JsonObject result = userAuth.authenticateUser("wrongUser", "wrongPassword");

    assertEquals(false, result.get("success").getAsBoolean());
    assertEquals("Log-in failed: Invalid username or password.", result.get("message").getAsString());
  }

}
