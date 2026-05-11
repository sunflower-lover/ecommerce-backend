package com.sunflower_lover.ecommerce_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "tax", precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

    @Column(name = "actual_delivery")
    private LocalDateTime actualDelivery;

    @Column(name = "notes", length = 1000)
    private String notes;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Calculate totals based on order items
   public void calculateTotals() {
        this.subtotal = items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.tax = subtotal.multiply(new BigDecimal("0.10")); //  tax rate of 10%
        this.shippingCost = subtotal.compareTo(new BigDecimal("100.00")) > 0 ? BigDecimal.ZERO : new BigDecimal("10.00"); // Free shipping for orders over $100 
        this.total = subtotal.add(tax).add(shippingCost);
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

    public User getUser() { 
        return user; 
    }

    public void setUser(User user) { 
        this.user = user; 
    }

    public List<OrderItem> getItems() { 
        return items; 
    }

    public void setItems(List<OrderItem> items) { 
        this.items = items; 
        calculateTotals() ; // Recalculate totals whenever items are updated
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

    public OrderStatus getStatus() { 
        return status; 
    }

    public void setStatus(OrderStatus status) { 
        this.status = status; 
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

    public PaymentStatus getPaymentStatus() { 
        return paymentStatus; 
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) { 
        this.paymentStatus = paymentStatus; 
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
    
    public LocalDateTime getEstimatedDelivery() { 
        return estimatedDelivery; 
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { 
        this.estimatedDelivery = estimatedDelivery; 
    }

    
    public LocalDateTime getActualDelivery() { 
        return actualDelivery; 
    }

    public void setActualDelivery(LocalDateTime actualDelivery) { 
        this.actualDelivery = actualDelivery; 
    }

    public String getNotes() { 
        return notes; 
    }

    public void setNotes(String notes) { 
        this.notes = notes; 
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
