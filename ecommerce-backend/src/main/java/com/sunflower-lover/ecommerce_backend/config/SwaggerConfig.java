package com.sunflower_lover.ecommerce_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("E-Commerce Backend API")
                .version("1.0")
                .description("Complete E-Commerce API with Authentication, Cart, and Orders")
                .contact(new Contact()
                    .name("Developer")
                    .email("dev@ecommerce.com")));
    }
}