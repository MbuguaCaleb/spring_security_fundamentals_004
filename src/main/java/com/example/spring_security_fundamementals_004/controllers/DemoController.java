package com.example.spring_security_fundamementals_004.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/demo")
    public String demoEndpoint(){

        return "hurray! two authentication's in an application";
    }
}
