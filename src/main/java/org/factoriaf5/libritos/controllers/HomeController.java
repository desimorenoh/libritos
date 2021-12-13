package org.factoriaf5.libritos.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller

public class HomeController {
    @GetMapping("/")
    public String home() {

        return "home";

    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}

