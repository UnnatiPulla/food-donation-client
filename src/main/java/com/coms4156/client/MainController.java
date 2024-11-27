package com.coms4156.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class MainController {

    private final RestTemplate restTemplate;

    public MainController(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.restTemplate = restTemplate;
    }

    public List<FoodListing> getFoodListings() {
        String url = "http://34.85.143.68:8080/getFoodListings?clientId=8";
        FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
        return Arrays.asList(listings);
    }

    public List<FoodListing> getNearbyListings(float latitude, float longitude, int maxDistance) {
        String url = String.format(
            "http://34.85.143.68:8080/"
                + "getNearbyListings?clientId=8&latitude=%f&longitude=%f&maxDistance=%d",
            latitude, longitude, maxDistance);
        FoodListing[] listings = restTemplate.getForObject(url, FoodListing[].class);
        return Arrays.asList(listings);
    }

    public ResponseEntity<FoodRequest> createFoodRequest(int clientId, int accountId,
                                                         int listingId, int quantityRequested) {
        String url =
            String.format("http://34.85.143.68:8080/api/foodRequests/"
                              + "create?clientId=8&accountId=17&listingId=%d&quantityRequested=%d",
                          listingId, quantityRequested);
        ResponseEntity<FoodRequest> response =
            restTemplate.postForEntity(url, null, FoodRequest.class);
        return response;
    }

    public FoodRequest fulfillRequest(int clientId, int listingId, int quantityRequested) {
        String url = String.format(
            "http://34.85.143.68:8080/fulfillRequest?clientId=8&listingId=%d&quantityRequested=%d",
            listingId, quantityRequested);
        FoodRequest response = restTemplate.patchForObject(url, null, FoodRequest.class);
        return response;
    }
}
