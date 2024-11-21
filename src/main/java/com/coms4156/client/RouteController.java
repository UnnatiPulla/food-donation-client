package com.coms4156.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @GetMapping("/homescreen")
    public String homescreen() {
        return "homescreen";
    }

    @GetMapping("/food-request")
    public String foodRequest() {
        return "food-request";
    }
}
