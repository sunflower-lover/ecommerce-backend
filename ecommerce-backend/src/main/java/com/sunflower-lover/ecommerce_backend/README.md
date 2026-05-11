# E-Commerce Backend API

A complete e-commerce backend built with Spring Boot, featuring user authentication, product management, shopping cart, and order processing.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.5.14
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- Maven

## ✨ Features

### Authentication & Authorization
- User registration and login
- JWT token-based authentication
- Role-based access (USER / ADMIN)

### Product Management
- Create, Read, Update, Delete products
- Search products by name
- Filter by category
- Stock management

### Shopping Cart
- Add/remove items from cart
- Update quantities
- Calculate total price

### Order Processing
- Create orders from cart
- View order history
- Admin: Update order status
- Admin: Add tracking numbers

## 📚 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create product (Admin) |
| PUT | `/api/products/{id}` | Update product (Admin) |
| DELETE | `/api/products/{id}` | Delete product (Admin) |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart` | Get user cart |
| POST | `/api/cart/add/{productId}` | Add to cart |
| PUT | `/api/cart/update/{productId}` | Update quantity |
| DELETE | `/api/cart/remove/{productId}` | Remove from cart |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Create order |
| GET | `/api/orders` | Get user orders |
| GET | `/api/orders/{id}` | Get order details |
| PUT | `/api/orders/{id}/cancel` | Cancel order |

### Admin
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| PUT | `/api/admin/orders/{id}/status` | Update order status |
| GET | `/api/admin/stats` | Dashboard statistics |

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven

### Steps
```bash
# Clone the repository
git clone https://github.com/sunflower_lover/ecommerce-backend.git

# Navigate to project
cd ecommerce-backend

# Run the application
./mvnw spring-boot:run