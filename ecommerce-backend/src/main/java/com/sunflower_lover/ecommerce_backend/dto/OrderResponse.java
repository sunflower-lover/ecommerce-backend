package com.sunflower_lover.ecommerce_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private List<OrderItemDTO> items;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private String status;
    private String paymentStatus;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String shippingMethod;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Inner DTO for order items
    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private String productSku;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
        
        // Getters and Setters
        public Long getProductId() { 
            return productId; 
        }

        public void setProductId(Long productId) { 
            this.productId = productId; 
        }
        
        public String getProductName() { 
            return productName; 
        }

        public void setProductName(String productName) { 
            this.productName = productName; 
        }

        public String getProductSku() { 
            return productSku; 
        }

        public void setProductSku(String productSku) { 
            this.productSku = productSku; 
        }
        
        public Integer getQuantity() { 
            return quantity; 
        }

        public void setQuantity(Integer quantity) { 
            this.quantity = quantity; 
        }
        
        public BigDecimal getPrice() { 
            return price; 
        }

        public void setPrice(BigDecimal price) { 
            this.price = price; 
        }
        
        public BigDecimal getSubtotal() { 
            return subtotal; 
        }

        public void setSubtotal(BigDecimal subtotal) { 
            this.subtotal = subtotal; 
        }
    }
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getOrderNumber() { 
        return orderNumber; 
    }
    
    public void setOrderNumber(String orderNumber) { 
        this.orderNumber = orderNumber; 
    }
    
    public String getCustomerName() { 
        return customerName; 
    }

    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }
    
    public String getCustomerEmail() { 
        return customerEmail; 
    }

    public void setCustomerEmail(String customerEmail) { 
        this.customerEmail = customerEmail; 
    }
    
    public List<OrderItemDTO> getItems() { 
        return items; 
    }

    public void setItems(List<OrderItemDTO> items) { 
        this.items = items; 
    }
    
    public BigDecimal getSubtotal() { 
        return subtotal; 
    }

    public void setSubtotal(BigDecimal subtotal) { 
        this.subtotal = subtotal; 
    }
    
    public BigDecimal getTax() { 
        return tax; 
    }

    public void setTax(BigDecimal tax) { 
        this.tax = tax; 
    }
    
    public BigDecimal getShippingCost() { 
        return shippingCost; 
    }

    public void setShippingCost(BigDecimal shippingCost) { 
        this.shippingCost = shippingCost; 
    }
    
    public BigDecimal getTotal() { 
        return total; 
    }

    public void setTotal(BigDecimal total) { 
        this.total = total; 
    }
    
    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public String getPaymentStatus() { 
        return paymentStatus; 
    }

    public void setPaymentStatus(String paymentStatus) { 
        this.paymentStatus = paymentStatus; 
    }
    
    public String getShippingAddress() { 
        return shippingAddress; 
    }

    public void setShippingAddress(String shippingAddress) { 
        this.shippingAddress = shippingAddress; 
    }
    
    public String getBillingAddress() { 
        return billingAddress; 
    }

    public void setBillingAddress(String billingAddress) { 
        this.billingAddress = billingAddress; 
    }
    
    public String getPaymentMethod() { 
        return paymentMethod; 
    }

    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod; 
    }
    
    public String getShippingMethod() { 
        return shippingMethod; 
    }

    public void setShippingMethod(String shippingMethod) { 
        this.shippingMethod = shippingMethod; 
    }
    
    public String getTrackingNumber() { 
        return trackingNumber; 
    }

    public void setTrackingNumber(String trackingNumber) { 
        this.trackingNumber = trackingNumber; 
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
