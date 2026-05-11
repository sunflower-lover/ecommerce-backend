package com.sunflower_lover.ecommerce_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class EcommerceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }
}

@Component
class PortListener {
    @EventListener
    public void onWebServerInitialized(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        
        System.out.println("E-COMMERCE BACKEND STARTED!");
        System.out.println("========================================");
        System.out.println("Test API: http://localhost:" + port + "/api/test/hello");
        System.out.println("Products API: http://localhost:" + port + "/api/products");
        System.out.println("H2 Console: http://localhost:" + port + "/h2-console");
        System.out.println("API Docs: http://localhost:" + port + "/v3/api-docs");
        System.out.println("Swagger UI: http://localhost:" + port + "/swagger-ui.html");
        System.out.println("========================================");
    
    }
}