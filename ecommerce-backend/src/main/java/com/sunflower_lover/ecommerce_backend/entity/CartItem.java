package com.sunflower_lover.ecommerce_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")     

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    private int quantity = 1;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private BigDecimal price;
    
    public CartItem() {}
    
    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
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

    public Cart getCart() { 
        return cart; 
    }

    public void setCart(Cart cart) { 
        this.cart = cart; 
    }

    public Product getProduct() {
        return product; 
    }

    public void setProduct(Product product) {
     this.product = product; 
     this.price = product.getPrice(); 
    }

    public int getQuantity() { 
        return quantity; 
    } 

    public void setQuantity(int quantity) { 
        this.quantity = quantity;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity)); 
    }

    public BigDecimal getPrice() { 
        return price; 
    }

    public void setPrice(BigDecimal price) { 
        this.price = price; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    
    }

    
}
