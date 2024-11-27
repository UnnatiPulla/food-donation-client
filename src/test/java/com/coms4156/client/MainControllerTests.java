package com.coms4156.client;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.coms4156.client.controller.MainController;
import com.coms4156.client.model.FoodListing;
import com.coms4156.client.model.FoodRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
  void testGetFoodListingsEmptyResponse() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenReturn(new FoodListing[0]);

    List<FoodListing> foodListings = mainController.getFoodListings();

    assertEquals(0, foodListings.size());
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testGetFoodListingsExceptionHandling() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenThrow(new RuntimeException("Error occurred"));

    assertThrows(RuntimeException.class, mainController::getFoodListings);
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testGetNearbyListings() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

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
    MainController mainController = new MainController(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenReturn(new FoodListing[0]);

    List<FoodListing> nearbyListings = mainController.getNearbyListings(40.7128f, -74.006f, 5);

    assertEquals(0, nearbyListings.size());
    verify(restTemplateMock, times(1)).getForObject(anyString(), eq(FoodListing[].class));
  }

  @Test
  void testGetNearbyListingsExceptionHandling() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(FoodListing[].class)))
        .thenThrow(new RuntimeException("Error occurred"));

    assertThrows(RuntimeException.class, () -> mainController.getNearbyListings(40.7128f, -74.006f, 5));
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

  @Test
  void testCreateFoodRequestExceptionHandling() {
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    MainController mainController = new MainController(restTemplateMock);

    when(restTemplateMock.postForEntity(anyString(), any(), eq(FoodRequest.class)))
        .thenThrow(new RuntimeException("Error occurred"));

    assertThrows(RuntimeException.class, () -> mainController.createFoodRequest(8, 17, 13, 2));
    verify(restTemplateMock, times(1)).postForEntity(anyString(), any(), eq(FoodRequest.class));
  }
}
