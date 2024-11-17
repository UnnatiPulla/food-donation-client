package com.example.food_donation_client.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class FoodListing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String foodName;
  private String description;
  private String location;
  private LocalDateTime availableUntil;
  private boolean isAvailable;

  // Getters and Setters
}
