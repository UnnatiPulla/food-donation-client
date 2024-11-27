package com.coms4156.client;
import com.google.gson.JsonObject;
import java.net.URL;
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
  void testRegisterUserEmptyUsername() throws Exception {
    JsonObject result = userAuth.registerUser("", "testPassword");

    assertEquals(false, result.get("success").getAsBoolean());
    assertEquals("Failed to register user: Username cannot be empty.",
        result.get("message").getAsString());
  }

  @Test
  void testRegisterUserFailure() throws Exception {
    // Mock failed registration response
    doReturn(mockConnection).when(userAuth).registerUserWithFirebase(anyString(), anyString());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

    JsonObject errorResponse = new JsonObject();
    JsonObject responseBody = new JsonObject();
    JsonObject errorDetails = new JsonObject();
    errorDetails.addProperty("message", "EMAIL_EXISTS");
    responseBody.add("error", errorDetails);
    errorResponse.addProperty("responseCode", HttpURLConnection.HTTP_BAD_REQUEST);
    errorResponse.add("responseBody", responseBody);

    doReturn(errorResponse).when(userAuth).getFirebaseResponse(mockConnection);

    JsonObject result = userAuth.registerUser("existingUser", "testPassword");

    assertEquals(false, result.get("success").getAsBoolean());
    assertEquals("Failed to register user: USERNAME_EXISTS", result.get("message").getAsString());
  }

  @Test
  void testAuthenticateUserSuccess() throws Exception {
    // Mock successful authentication response
    doReturn(mockConnection).when(userAuth).authenticateUserWithFirebase(anyString(), anyString());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

    JsonObject mockResponse = new JsonObject();
    mockResponse.addProperty("responseCode", HttpURLConnection.HTTP_OK);
    mockResponse.add("responseBody", new JsonObject());

    doReturn(mockResponse).when(userAuth).getFirebaseResponse(mockConnection);

    JsonObject result = userAuth.authenticateUser("validUser", "validPassword");

    assertEquals(true, result.get("success").getAsBoolean());
    assertEquals("Log-in successful!", result.get("message").getAsString());
  }

  @Test
  void testAuthenticateUserUnknownError() throws Exception {
    // Mock unknown error response
    doReturn(mockConnection).when(userAuth).authenticateUserWithFirebase(anyString(), anyString());
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);

    JsonObject mockResponse = new JsonObject();
    mockResponse.addProperty("responseCode", HttpURLConnection.HTTP_INTERNAL_ERROR);

    doReturn(mockResponse).when(userAuth).getFirebaseResponse(mockConnection);

    JsonObject result = userAuth.authenticateUser("testUser", "testPassword");

    assertEquals(false, result.get("success").getAsBoolean());
    assertEquals("Unknown error occurred during authentication.",
        result.get("message").getAsString());
  }

}
