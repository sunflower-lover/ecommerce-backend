# E-Commerce Backend API

A complete e-commerce backend built with Spring Boot, featuring authentication, product management, shopping cart, and order processing.

## Technologies 

- Java 17
- Spring Boot 3.5.14
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- Maven

## Features 

-**Authentication**: User registration, login, JWWT tokens
-**Products**: Full CRUD operations, search by name/category
-**Cart**: Add/remove items, update quantities
-**Orders**: Create orders from cart, order history
-**Admin**: User management, order staus updates

## Quick Start
'''bash
git clone https://github.com/sunflower-lover/ecommerce-backend.git
cd ecommerce-backend
./mvnw spring-boot:run
