package com.lcwd.electronic.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Homecontroller {
    @GetMapping
    public String getstring() {
        return "hi iamm rupesh";
    }
}
