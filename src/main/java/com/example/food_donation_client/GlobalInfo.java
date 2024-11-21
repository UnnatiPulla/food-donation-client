package com.example.food_donation_client;

import org.springframework.web.client.RestTemplate;

/**
 * GlobalInfo is a utility class that holds valuable constants and global references
 * for use across the application.
 */
public class GlobalInfo {

  /**
   * A global static reference for making HTTP requests.
   */
  public static RestTemplate restTemplate;

  /**
   * Base URL of the service. Update this as needed for different environments.
   */
  public static final String SERVICE_BASE_URL = "http://127.0.0.1:8080";//"http://34.48.194.87:8080";

  /**
   * Endpoint for retrieving all food listings.
   */
  public static final String GET_FOOD_LISTINGS_URI = "/getFoodListings";

  /**
   * Endpoint for retrieving nearby food listings.
   */
  public static final String GET_NEARBY_FOOD_LISTINGS_URI = "/getNearbyListings";

  /**
   * Endpoint for registering users.
   */
  public static final String REGISTER_USER_URI = "/api/registerUser";

  /**
   * A String representing the specific client ID. Update as needed for testing or concurrency.
   */
  public static final String CLIENT_ID = "DefaultClientID12345";

  /**
   * Assigns the global RestTemplate instance for HTTP operations.
   *
   * @param rt The RestTemplate used for making HTTP requests.
   */
  public static void assignRestTemplate(RestTemplate rt) {
    restTemplate = rt;
  }

  /**
   * Constructs a full URL by appending the endpoint to the base service URL.
   *
   * @param endpoint The endpoint to append.
   * @return The full URL.
   */
  public static String buildFullUrl(String endpoint) {
    return SERVICE_BASE_URL + endpoint;
  }
}
