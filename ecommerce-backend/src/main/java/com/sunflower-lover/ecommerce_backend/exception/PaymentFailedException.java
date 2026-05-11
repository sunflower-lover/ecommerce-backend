package com.sunflower_lover.ecommerce_backend.exception;

public class PaymentFailedException extends RuntimeException {
    
    private String transactionId;
    
    public PaymentFailedException(String message) {
        super(message);
    }
    
    public PaymentFailedException(String message, String transactionId) {
        super(message);
        this.transactionId = transactionId;
    }
    
    public String getTransactionId() { 
        return transactionId; 
    }
    
}
