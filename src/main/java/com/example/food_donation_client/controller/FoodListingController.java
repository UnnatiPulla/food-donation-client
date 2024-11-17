package com.example.food_donation_client.controller;

import com.example.food_donation_client.model.FoodListing;
import com.example.food_donation_client.service.FoodListingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/foodlistings")
public class FoodListingController {
  private final FoodListingService foodListingService;

  public FoodListingController(FoodListingService foodListingService) {
    this.foodListingService = foodListingService;
  }

  @GetMapping("/available")
  public List<FoodListing> getAvailableFoodListings() {
    return foodListingService.getAvailableFoodListings();
  }
}

