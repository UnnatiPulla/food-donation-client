package com.coms4156.client;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class UserAuthentication {
  private static final String WEB_API_KEY = "AIzaSyAu7LvjwHhars6EgcYFcB4mQwJP2Qs013s";
  private static FirebaseAuth firebaseAuth;

  @PostConstruct
  public void initializeFirebase() {
    try {
      FileInputStream serviceAccount = new FileInputStream(
          "src/main/java/com/coms4156/client/firebase-config.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        firebaseAuth = FirebaseAuth.getInstance();
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize Firebase: " + e.getMessage());
    }

  }

  /**
   * Registers a user with the given credentials.
   * If a user with `username` already exists
   * or if an error occurred in the process of checking
   * if such a user already exists, returns false.
   * Otherwise, returns True.
   *
   * @param username Username
   * @param password Password
   * @return Whether the user was successfully registered.
   */
  public JsonObject registerUser(String username, String password) throws IOException {
    System.out.println("Registering user " + username + " with password " + password);

    String email = username + "@app.com";
    JsonObject result = new JsonObject();

    if (doesUserExist(email, password)) {
      result.addProperty("success", false);
      result.addProperty("message", "Failed to register user with username <"
          + username + ">. User already exists.");
      return result;
    } else {
      System.out.println("USER DOESN'T EXIST!");

      // Use Firebase Authentication API to register a user
      HttpURLConnection conn = registerUserWithFirebase(email, password);
      JsonObject response = getFirebaseResponse(conn);

      // Parse the response
      if (response.get("responseCode").getAsInt() == HttpURLConnection.HTTP_OK) {
        result.addProperty("success", true);
        result.addProperty("message", "User registration successful!");
        return result;
      } else {
        result.addProperty("success", false);

        result.addProperty("message", "User registration failed: "
            + response.get("responseBody"));
        return result;
      }
    }
  }

  public JsonObject authenticateUser(String username, String password) throws IOException {
    System.out.println("Authenticating user " + username);

    String email = username + "@app.com";
    JsonObject result = new JsonObject();

    if (doesUserExist(email, password)) {
      HttpURLConnection conn = authenticateUserWithFirebase(email, password);
      JsonObject response = getFirebaseResponse(conn);
      if (response.get("responseCode").getAsInt() == HttpURLConnection.HTTP_OK) {
        result.addProperty("success", true);
        result.addProperty("message", "Log-in successful!");
      } else {
        result.addProperty("success", false);
        result.addProperty("message", "Log-in failed: incorrect password.");
      }

    } else {
      result.addProperty("success", false);
      result.addProperty("message", "Log-in failed: username not found.");
    }
    return result;
  }


  //===BEGIN HELPER METHODS===//
  /**
   * Makes an API call to Firebase Authentication to register a new client.
   *
   * @param email User email
   * @param password User password
   * @return The response code
   * @throws IOException if an I/O error occurs while sending or receiving data,
   *         or if the connection's input or error stream cannot be accessed or read.
   *
   */
  private HttpURLConnection registerUserWithFirebase(String email, String password) throws IOException {
    URL url = new URL(
        "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key="
            + WEB_API_KEY
    );
    return getHttpURLConnection(email, password, url);
  }

  /**
   * Authenticates a user with Firebase using their email and password
   * by sending a POST request to the Firebase Authentication API.
   *
   * @param email The email address of the user attempting to authenticate.
   * @param password The password of the user attempting to authenticate.
   * @return The HttpURLConnection object representing the open connection for the request.
   * @throws IOException If an I/O error occurs while opening the connection or sending the request.
   */
  private HttpURLConnection authenticateUserWithFirebase(String email, String password) throws IOException {
    URL url = new URL(
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="
              + WEB_API_KEY
    );
    return getHttpURLConnection(email, password, url);
  }

  /**
   * Establishes an HTTP connection to a given URL and prepares a POST request to
   * send user credentials in JSON format.
   *
   * @param email The email address of the user attempting to authenticate.
   * @param password The password of the user attempting to authenticate.
   * @param url The URL to which the POST request is sent, typically the Firebase Authentication API endpoint.
   * @return The HttpURLConnection object representing the open connection for the request.
   * @throws IOException If an I/O error occurs while opening the connection or writing the request body.
   */
  private HttpURLConnection getHttpURLConnection(String email, String password, URL url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");

    JsonObject payload = new JsonObject();
    payload.addProperty("email", email);
    payload.addProperty("password", password);
    payload.addProperty("returnSecureToken", true);

    OutputStream os = conn.getOutputStream();
    os.write(payload.toString().getBytes());
    os.flush();

    return conn;
  }

  /**
   * Retrieves the response from a Firebase API call, including the HTTP response code
   * and the response body (for both success and error responses).
   *
   * @param conn The HttpURLConnection object used for the Firebase API call.
   * @return A JsonObject containing:
   *         - "responseCode": The HTTP response code as an integer.
   *         - "responseBody": The response body as a string.
   * @throws IOException If an I/O error occurs while reading the response stream or
   *         retrieving the HTTP response code.
   */
  private JsonObject getFirebaseResponse(HttpURLConnection conn) throws IOException {
    JsonObject response = new JsonObject();
    response.addProperty("responseCode", conn.getResponseCode());

    InputStream responseStream;
    if (conn.getResponseCode() >= 400) { // Error response
      responseStream = conn.getErrorStream();
    } else { // Success response
      responseStream = conn.getInputStream();
    }

    String responseBody = new BufferedReader(new InputStreamReader(responseStream))
        .lines()
        .collect(Collectors.joining("\n"));
    response.addProperty("responseBody", responseBody);
    return response;
  }

  /**
   * Checks whether a user exists in the Firebase Authentication by their email.
   * If the user exists, returns true. If the user does not exist
   * (Firebase Authentication throws a USER_NOT_FOUND exception),
   * returns false. All other exceptions are re-thrown.
   *
   * @param email User email
   * @param password User password
   * @return Whether the given user exists
   */
  private boolean doesUserExist(String email, String password) {
    try {
      firebaseAuth.getUserByEmail(email);
      return true;
    } catch (FirebaseAuthException e) {
      System.out.println("EXCEPTION THROWN in doesUserExist");
      System.out.println(e.getMessage());
      System.out.println(e.getErrorCode());

      if (isUserNotFound(e)) {
        return false;
      } else {
        throw new RuntimeException(String.valueOf(e.getAuthErrorCode()));
      }
    }
  }

  /**
   * Determines whether an exception that occurred during an attempt
   * to retrieve a user by their email using Firebase Authentication
   * is the `USER_NOT_FOUND` exception.
   *
   * @param e An exception thrown by Firebase Authentication
   * @return Whether the exception thrown by Firebase Authentication
   *        in an attempt to retrieve a user is the `USER_NOT_FOUND` exception.
   */
  private boolean isUserNotFound(Exception e) {
    return e instanceof FirebaseAuthException &&
        ((FirebaseAuthException) e).getAuthErrorCode() == AuthErrorCode.USER_NOT_FOUND;
  }

  //===END HELPER METHODS===//
}
