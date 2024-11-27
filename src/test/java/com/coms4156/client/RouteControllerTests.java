package com.coms4156.client;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteControllerTests {

  @Test
  void testSearchResults() {
    MainController mainControllerMock = mock(MainController.class); // Mock MainController
    RouteController routeController = new RouteController();
    routeController.setMainController(mainControllerMock); // Inject mock using setter

    List<FoodListing> mockListings = new ArrayList<>();
    when(mainControllerMock.getNearbyListings(anyFloat(), anyFloat(), anyInt()))
        .thenReturn(mockListings);

    Model modelMock = mock(Model.class);

    String viewName = routeController.searchResults(modelMock, 40.7128f, -74.006f);

    assertEquals("searchresults", viewName);
    verify(modelMock, times(1)).addAttribute(eq("foodListings"), eq(mockListings));
  }

}

