package com.coms4156.client;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.coms4156.client.controller.ServiceHelper;
import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ServiceHelperTests {

  @Test
  void testGetNearbyListings() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    ServiceHelper mainController = new ServiceHelper(restTemplateMock);

    FoodListing[] mockResponse = {new FoodListing()};
    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenReturn(mockResponse);

    List<FoodListing> nearbyListings = mainController.getNearbyListings(40.7128f, -74.006f, 5);

    assertEquals(1, nearbyListings.size());
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testGetNearbyListingsEmptyResponse() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    ServiceHelper mainController = new ServiceHelper(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenReturn(new FoodListing[0]);

    List<FoodListing> nearbyListings = mainController.getNearbyListings(40.7128f, -74.006f, 5);

    assertEquals(0, nearbyListings.size());
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testGetNearbyListingsExceptionHandling() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    ServiceHelper mainController = new ServiceHelper(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenThrow(new RuntimeException("Error occurred"));

    assertThrows(RuntimeException.class, () -> mainController.getNearbyListings(40.7128f, -74.006f, 5));
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }
}
