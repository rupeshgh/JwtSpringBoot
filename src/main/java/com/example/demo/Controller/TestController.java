package com.example.demo.Controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {




    @GetMapping("")
    public void wel(){

        System.out.println("Roles sepecific123");
    }
    @GetMapping("/hello")
    public void wels(){

        System.out.println("Hello hello");
    }


}
