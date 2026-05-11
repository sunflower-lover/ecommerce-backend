package com.sunflower_lover.ecommerce_backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotBlank(message = "SKU is required")
    @Pattern(regexp = "^[A-Z 0-9-]+$", message = "SKU must contain only uppercase letters, numbers, and hyphens")
    private String sku;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private BigDecimal price;
    
    @Min(value = 0, message = "Stock cannot be negative")
    @Max(value = 999999, message = "Stock cannot exceed 999999")
    private Integer stockQuantity = 0;
    
    private String imageUrl;
    private String category;
    private Boolean active = true;
    
    public ProductDTO() {}
    
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
    
    public Boolean getActive() { 
        return active; 
    }

    public void setActive(Boolean active) { 
        this.active = active; 
    }
    
}
