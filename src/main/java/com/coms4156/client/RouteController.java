package com.coms4156.client;


import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @Autowired private MainController mainController;

    @GetMapping("/homescreen")
    public String homescreen() {
        return "homescreen";
    }

    @GetMapping("/searchresults")
    public String searchResults(Model model, 
	  @RequestParam("latitude") float latitude,
	  @RequestParam("longitude") float longitude) {
      List<FoodListing> listings = mainController.getNearbyListings(latitude, longitude, 500);
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      listings.forEach(listing -> {
        if (listing.getEarliestPickUpTime() != null) {
            listing.setFormattedPickUpTime(listing.getEarliestPickUpTime().format(formatter));
        }
     });
      model.addAttribute("foodListings", listings);
      return "searchresults";
    }

	@GetMapping("/quantityrequest")
	public String quantityRequest() {
		return "quantityrequest";
	}

    @GetMapping("/food-request")
    public String foodRequest() {
        List<FoodListing> listings = mainController.getFoodListings();
        for (FoodListing l : listings) {
            System.out.println(l.getFoodType() + " " + l.getQuantityListed() + " " +
                               l.getEarliestPickUpTime() + " " + l.getLatitude() + " " +
                               l.getLongitude());
        }

        return "food-request";
    }
  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }
}
