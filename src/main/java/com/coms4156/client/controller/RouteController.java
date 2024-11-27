package com.coms4156.client.controller;

import com.coms4156.client.model.FoodListing;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @Autowired private MainController mainController;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/search-results")
    public String searchResults(Model model, @RequestParam("latitude") float latitude,
                                @RequestParam("longitude") float longitude) {
        List<FoodListing> listings = mainController.getNearbyListings(latitude, longitude, 500);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        listings.forEach(listing -> {
            if (listing.getEarliestPickUpTime() != null) {
                listing.setFormattedPickUpTime(listing.getEarliestPickUpTime().format(formatter));
            }
        });
        model.addAttribute("foodListings", listings);
        return "search-results";
    }

    @PostMapping("/quantity-request")
    public String quantityRequest(@RequestParam("listingId") int listingId,
                                  @RequestParam("foodType") String foodType,
                                  @RequestParam("quantityListed") int quantityListed,
                                  @RequestParam("formattedPickUpTime") String formattedPickUpTime,
                                  @RequestParam("latitude") float latitude,
                                  @RequestParam("longitude") float longitude, Model model) {

        model.addAttribute("listingId", listingId);
        model.addAttribute("foodType", foodType);
        model.addAttribute("quantityListed", quantityListed);
        model.addAttribute("formattedPickUpTime", formattedPickUpTime);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);

        // Return the request page view
        return "quantity-request";
    }

    @PostMapping("/submit-request")
    public String submitRequest(@RequestParam("listingId") int listingId,
                                @RequestParam("quantityRequested") int quantityRequested) {
        // TODO: Move this to a Globals.java class.
        int clientId = 8;
        mainController.fulfillRequest(clientId, listingId, quantityRequested);
        return "submit-request";
    }

    // Testing purposes
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
