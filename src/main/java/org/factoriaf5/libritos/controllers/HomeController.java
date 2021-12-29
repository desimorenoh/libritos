package org.factoriaf5.libritos.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@CrossOrigin
public class HomeController {
    @GetMapping("/")
    public String home() {

        return "redirect:/home";

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}



