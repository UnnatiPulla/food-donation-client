package com.coms4156.client;

import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RouteControllerTests {

  @Test
  void testHomescreen() {
    RouteController routeController = new RouteController();

    String viewName = routeController.homescreen();

    assertEquals("homescreen", viewName);
  }

  @Test
  void testSearchResults() {
    MainController mainControllerMock = mock(MainController.class);
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock);

    List<FoodListing> mockListings = new ArrayList<>();
    FoodListing listing = new FoodListing();
    listing.setEarliestPickUpTime(LocalDateTime.now());
    mockListings.add(listing);

    when(mainControllerMock.getNearbyListings(anyFloat(), anyFloat(), anyInt()))
        .thenReturn(mockListings);

    Model modelMock = mock(Model.class);

    String viewName = routeController.searchResults(modelMock, 40.7128f, -74.006f);

    assertEquals("searchresults", viewName);
    verify(modelMock, times(1)).addAttribute(eq("foodListings"), eq(mockListings));
    assertEquals(listing.getEarliestPickUpTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        listing.getFormattedPickUpTime());
  }

  @Test
  void testSearchResultsWithEmptyListings() {
    MainController mainControllerMock = mock(MainController.class);
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock);

    when(mainControllerMock.getNearbyListings(anyFloat(), anyFloat(), anyInt()))
        .thenReturn(new ArrayList<>());

    Model modelMock = mock(Model.class);

    String viewName = routeController.searchResults(modelMock, 40.7128f, -74.006f);

    assertEquals("searchresults", viewName);
    verify(modelMock, times(1)).addAttribute(eq("foodListings"), eq(new ArrayList<>()));
  }

  @Test
  void testQuantityRequest() {
    RouteController routeController = new RouteController();

    String viewName = routeController.quantityRequest();

    assertEquals("quantityrequest", viewName);
  }

  @Test
  void testFoodRequest() {
    MainController mainControllerMock = mock(MainController.class);
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock);

    List<FoodListing> mockListings = new ArrayList<>();
    FoodListing listing = new FoodListing();
    listing.setFoodType("Bread");
    listing.setQuantityListed(10);
    listing.setLatitude(40.7128f);
    listing.setLongitude(-74.006f);
    mockListings.add(listing);

    when(mainControllerMock.getFoodListings()).thenReturn(mockListings);

    String viewName = routeController.foodRequest();

    assertEquals("food-request", viewName);
    verify(mainControllerMock, times(1)).getFoodListings();
  }

  @Test
  void testFoodRequestWithNoListings() {
    MainController mainControllerMock = mock(MainController.class);
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock);

    when(mainControllerMock.getFoodListings()).thenReturn(new ArrayList<>());

    String viewName = routeController.foodRequest();

    assertEquals("food-request", viewName);
    verify(mainControllerMock, times(1)).getFoodListings();
  }
}
