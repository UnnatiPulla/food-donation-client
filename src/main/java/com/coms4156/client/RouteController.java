package com.coms4156.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @Autowired private MainController mainController;

    @GetMapping("/homescreen")
    public String homescreen() {
        return "homescreen";
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
}
