package com.sunflower_lover.ecommerce_backend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @NotBlank(message = "SKU is required")
    @Column(unique = true, nullable = false)
    private String sku;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    private String imageUrl;
    
    private String category;
    
    private boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Product() {}
    
    public Product(String name, String description, String sku, BigDecimal price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public String getSku() { 
        return sku; 
    }

    public void setSku(String sku) { 
        this.sku = sku; 
    }
    
    public BigDecimal getPrice() { 
        return price; 
    }

    public void setPrice(BigDecimal price) { 
        this.price = price; 
    }
    
    public Integer getStockQuantity() { 
        return stockQuantity; 
    }

    public void setStockQuantity(Integer stockQuantity) { 
        this.stockQuantity = stockQuantity; 
    }
    
    public String getImageUrl() { 
        return imageUrl; 
    }

    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl; 
    }
    
    public String getCategory() { 
        return category; 
    }

    public void setCategory(String category) { 
        this.category = category; 
    }
    
    public boolean isActive() { 
        return active; 
    }

    public void setActive(boolean active) { 
        this.active = active; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
    
}

