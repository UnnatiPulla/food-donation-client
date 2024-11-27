package com.coms4156.client;

import java.time.LocalDateTime;

public class FoodListing {
	private int listingId;
    private String foodType;
    private int quantityListed;
    private LocalDateTime earliestPickUpTime;
    private float latitude;
    private float longitude;
    private String formattedPickUpTime;

    public String getFoodType() {
        return foodType;
    }
    public int getQuantityListed() {
        return quantityListed;
    }
    public LocalDateTime getEarliestPickUpTime() {
        return earliestPickUpTime;
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
    public float getLongitude() {
        return longitude;
    }
    public int getListingId() {
        return listingId;
    }
    public void setListingId(int listingId) { 
        this.listingId = listingId;
    }
}