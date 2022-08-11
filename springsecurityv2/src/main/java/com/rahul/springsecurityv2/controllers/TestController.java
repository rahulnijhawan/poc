package com.rahul.springsecurityv2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {
    @GetMapping("public")
    public String checkPublic() {
        return "home";
    }

    @PostMapping("secure")
    public String checkSecure() {
        System.out.println("post :(");
        return "home";
    }

    @PutMapping("put")
    public String put() {
        System.out.println("put :(");
        return "home";
    }

    @DeleteMapping("delete")
    public String delete() {
        System.out.println("delete :(");
        return "home";
    }

    @GetMapping
    public String home() {
        return "home";
    }
}
