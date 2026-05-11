package com.sunflower_lover.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderRequest {
    
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    
    @NotBlank(message = "Billing address is required")
    private String billingAddress;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private String shippingMethod;
    
    private String notes;
    
    // Getters and Setters
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
    
    public String getNotes() { 
        return notes; 
    }
    
    public void setNotes(String notes) { 
        this.notes = notes; 
    }
}
