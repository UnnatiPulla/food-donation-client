package com.example.food_donation_client;

import com.example.food_donation_client.FoodListing;
import java.util.Collections;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.example.food_donation_client.GlobalInfo.*;

@Service
public class FoodListingService {

  /**
   * Retrieves all food listings for a given client ID.
   *
   * @return A list of food listings, or null if none are found.
   */
  public List<FoodListing> getAllAvailableFoodListings() {
    String url = GlobalInfo.buildFullUrl(GlobalInfo.GET_FOOD_LISTINGS_URI) + "?clientId=" + 8;
    //System.out.println(url);
    try {
      ResponseEntity<FoodListing[]> response = GlobalInfo.restTemplate.getForEntity(url, FoodListing[].class);
      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        return Arrays.asList(response.getBody());
      } else {
        System.out.println("No food listings found. Returning empty list.");
        return Collections.emptyList(); // Return an empty list instead of null.
      }
    } catch (Exception e) {
      System.out.println("Error fetching food listings: " + e.getMessage());
      return Collections.emptyList(); // Return an empty list if an error occurs.
    }
  }


  /**
   * Retrieves nearby food listings for a given client ID and location.
   *
   * @param clientId    The ID of the client.
   * @param latitude    The latitude of the query location.
   * @param longitude   The longitude of the query location.
   * @param maxDistance The maximum distance from the location.
   * @return A list of nearby food listings, or null if none are found.
   */
  public List<FoodListing> getNearbyListings(int clientId, float latitude, float longitude, int maxDistance) {
    String url = buildFullUrl(GET_NEARBY_FOOD_LISTINGS_URI) +
        "?clientId=" + 8 +
        "&latitude=" + latitude +
        "&longitude=" + longitude +
        "&maxDistance=" + maxDistance;
    try {
      ResponseEntity<FoodListing[]> response = restTemplate.getForEntity(url, FoodListing[].class);
      if (response.getStatusCode().is2xxSuccessful()) {
        return Arrays.asList(response.getBody());
      }
    } catch (Exception e) {
      System.out.println("Error fetching nearby listings: " + e.getMessage());
    }
    return null;
  }

}
