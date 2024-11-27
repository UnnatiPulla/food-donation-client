package com.coms4156.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MainControllerTests {

  @Test
  void testGetFoodListings() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    FoodListing[] mockResponse = {new FoodListing()};
    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenReturn(mockResponse);

    List<FoodListing> foodListings = mainController.getFoodListings();

    assertEquals(1, foodListings.size());
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testCreateFoodRequest() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    ResponseEntity<FoodRequest> mockResponse = ResponseEntity.ok(new FoodRequest());
    when(restTemplateMock.postForEntity(anyString(), any(), eq(FoodRequest.class)))
        .thenReturn(mockResponse);

    ResponseEntity<FoodRequest> response = mainController.createFoodRequest(8, 17, 13, 2);

    assertEquals(200, response.getStatusCodeValue());
    verify(restTemplateMock, times(1)).postForEntity(anyString(), any(), eq(FoodRequest.class));
  }
}

