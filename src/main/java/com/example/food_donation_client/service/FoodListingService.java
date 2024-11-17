package com.example.food_donation_client.service;

import com.example.food_donation_client.model.FoodListing;
import com.example.food_donation_client.repository.FoodListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodListingService {
  private final FoodListingRepository foodListingRepository;

  public FoodListingService(FoodListingRepository foodListingRepository) {
    this.foodListingRepository = foodListingRepository;
  }

  public List<FoodListing> getAvailableFoodListings() {
    return foodListingRepository.findByIsAvailableTrue();
  }
}
