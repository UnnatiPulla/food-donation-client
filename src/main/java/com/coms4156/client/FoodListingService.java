package com.coms4156.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FoodListingService {

    private final RestTemplate restTemplate;

    public FoodListingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FoodListing> getFoodListings() {
        String url = "http://34.85.143.68:8080/getFoodListings?clientId=8";
        FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
        return Arrays.asList(listings);
    }
}
