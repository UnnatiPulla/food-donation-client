package com.coms4156.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MainController {

    private final RestTemplate restTemplate;

    public MainController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FoodListing> getFoodListings() {
        String url = "http://34.85.143.68:8080/getFoodListings?clientId=8";
        FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
        return Arrays.asList(listings);
    }

    public List<FoodListing> getNearbyListings(float latitude, float longitude, int maxDistance) {
        String url = String.format("http://34.85.143.68:8080/getNearbyListings?clientId=8&latitude=%f&longitude=%f&maxDistance=%d", latitude, longitude, maxDistance);
        FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
        return Arrays.asList(listings);
    }

}
