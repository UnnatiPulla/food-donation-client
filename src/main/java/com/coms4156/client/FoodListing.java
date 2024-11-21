package com.coms4156.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class FoodListing {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private String foodType;
    private int quantityListed;
    private LocalDateTime earliestPickUpTime;
    private float latitude;
    private float longitude;

    public String getFoodType() {
        return foodType;
    }
    public int getQuantityListed() {
        return quantityListed;
    }
    public LocalDateTime getEarliestPickUpTime() {
        return earliestPickUpTime;
    }
    public float getLatitude() {
        return latitude;
    }
    public float getLongitude() {
        return longitude;
    }
}
