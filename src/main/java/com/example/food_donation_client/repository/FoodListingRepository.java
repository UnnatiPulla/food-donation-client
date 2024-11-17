package com.example.food_donation_client.repository;

import com.example.food_donation_client.model.FoodListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodListingRepository extends JpaRepository<FoodListing, Long> {
  List<FoodListing> findByIsAvailableTrue();
}

