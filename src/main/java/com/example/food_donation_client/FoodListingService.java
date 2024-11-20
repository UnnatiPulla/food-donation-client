package com.example.food_donation_client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FoodListingService {

  private final RestTemplate restTemplate;

  public FoodListingService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<FoodListing> getAllAvailableFoodListings() {
    String url = "http://localhost:8080/getFoodListings?clientId=" + 4;
    FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
    return Arrays.asList(listings);
  }
}

