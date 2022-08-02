package com.rahul.springsecurityv2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("public")
    public String checkPublic() {
        return "public";
    }

    @GetMapping("secure")
    public String checkSecure() {
        return "secure  ";
    }

    @GetMapping
    public String home() {
        return "home.html";
    }
}
