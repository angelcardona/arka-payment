package com.arka.payments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestPayment {
    @GetMapping
    public String hello(){
        return "HEllo from payments Service";
    }
}
