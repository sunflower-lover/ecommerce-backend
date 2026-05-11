package com.sunflower_lover.ecommerce_backend.exception;

public class InsufficientStockException extends RuntimeException {
    
    private Long productId;
    private String productName;
    private int requestedQuantity;
    private int availableQuantity;
    
    public InsufficientStockException(Long productId, String productName, int requestedQuantity, int availableQuantity) {
        super(String.format("Insufficient stock for product '%s'. Requested: %d, Available: %d", 
              productName, requestedQuantity, availableQuantity));
        this.productId = productId;
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }
    
    // Getters
    public Long getProductId() { 
        return productId; 
    }

    public String getProductName() { 
        return productName; 
    }

    public int getRequestedQuantity() { 
        return requestedQuantity; 
    }
    
    public int getAvailableQuantity() { 
        return availableQuantity; 
    }
}
