package com.coms4156.client.controller;

import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ServiceHelper {

    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    public ServiceHelper(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.restTemplate = restTemplate;

        this.uriBuilder =
            UriComponentsBuilder.newInstance().scheme("http").host("34.85.143.68").port(8080);
    }

    public List<FoodListing> getNearbyListings(float latitude, float longitude, int maxDistance) {
        try {
            URL url = uriBuilder.cloneBuilder()
                          .path("getNearbyListings")
                          .queryParam("clientId", 8)
                          .queryParam("latitude", latitude)
                          .queryParam("longitude", longitude)
                          .queryParam("maxDistance", maxDistance)
                          .build()
                          .toUri()
                          .toURL();

            FoodListing[] listings =
                restTemplate.getForObject(url.toString(), FoodListing[].class);
            return Arrays.asList(listings);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public FoodRequest fulfillRequest(int listingId, int quantityRequested) {
        try {
            URL url = uriBuilder.cloneBuilder()
                          .path("fulfillRequest")
                          .queryParam("clientId", 8)
                          .queryParam("listingId", listingId)
                          .queryParam("quantityRequested", quantityRequested)
                          .build()
                          .toUri()
                          .toURL();

            FoodRequest foodRequest =
                restTemplate.patchForObject(url.toString(), null, FoodRequest.class);
            return foodRequest;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
