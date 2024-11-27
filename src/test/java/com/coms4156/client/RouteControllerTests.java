package com.coms4156.client;
import com.coms4156.client.controller.MainController;
import com.coms4156.client.controller.RouteController;
import com.coms4156.client.model.FoodListing;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RouteControllerTests {

  @Test
  void testHome() {
    RouteController routeController = new RouteController();
    String viewName = routeController.home();
    assertEquals("home", viewName);
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

    assertEquals("search-results", viewName);
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

    assertEquals("search-results", viewName);
    verify(modelMock, times(1)).addAttribute(eq("foodListings"), eq(new ArrayList<>()));
  }

  @Test
  void testQuantityRequest() {
    RouteController routeController = new RouteController();
    Model modelMock = mock(Model.class);

    String viewName = routeController.quantityRequest(
        1, "Bread", 10, "2024-12-01 12:00", 40.7128f, -74.006f, modelMock
    );

    assertEquals("quantity-request", viewName);
    verify(modelMock).addAttribute("listingId", 1);
    verify(modelMock).addAttribute("foodType", "Bread");
    verify(modelMock).addAttribute("quantityListed", 10);
    verify(modelMock).addAttribute("formattedPickUpTime", "2024-12-01 12:00");
    verify(modelMock).addAttribute("latitude", 40.7128f);
    verify(modelMock).addAttribute("longitude", -74.006f);
  }

  @Test
  void testSubmitRequest() {
    MainController mainControllerMock = mock(MainController.class);
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock);

    String viewName = routeController.submitRequest(1, 5);

    assertEquals("submit-request", viewName);
    verify(mainControllerMock, times(1)).fulfillRequest(8, 1, 5);
  }
}
