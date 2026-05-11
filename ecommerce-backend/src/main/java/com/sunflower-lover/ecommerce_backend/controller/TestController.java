package com.sunflower_lover.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "E-Commerce API is Working!");
        response.put("status", "success");
        return response;
    }

    @GetMapping("/swagger-test")
public String swaggerTest() {
    return "Swagger should be available at /swagger-ui.html";
    }
}