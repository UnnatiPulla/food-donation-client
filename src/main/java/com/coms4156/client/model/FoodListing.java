package com.coms4156.client.model;

import java.time.LocalDateTime;

public class FoodListing {
    private int listingId;
    private String foodType;
    private int quantityListed;
    private LocalDateTime earliestPickUpTime;
    private float latitude;
    private float longitude;
    private String formattedPickUpTime;

    public int getListingId() {
        return listingId;
    }
    public void setListingId(int listingId) {
        this.listingId = listingId;
    }
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    public int getQuantityListed() {
        return quantityListed;
    }
    public void setQuantityListed(int quantityListed) {
        this.quantityListed = quantityListed;
    }
    public LocalDateTime getEarliestPickUpTime() {
        return earliestPickUpTime;
    }

    public void setEarliestPickUpTime(LocalDateTime earliestPickUpTime) {
        this.earliestPickUpTime = earliestPickUpTime;
    }
    public String getFormattedPickUpTime() {
        return formattedPickUpTime;
    }
    public void setFormattedPickUpTime(String formattedPickUpTime) {
        this.formattedPickUpTime = formattedPickUpTime;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
