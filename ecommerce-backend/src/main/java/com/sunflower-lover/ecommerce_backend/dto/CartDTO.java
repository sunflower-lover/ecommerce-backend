package com.sunflower_lover.ecommerce_backend.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private Long id;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
    private int itemCount;

   
    // Getters and Setters
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public List<CartItemDTO> getItems() { 
        return items; 
    }

    public void setItems(List<CartItemDTO> items) { 
        this.items = items; 
    }

    public BigDecimal getTotalPrice() { 
        return totalPrice; 
    }

    public void setTotalPrice(BigDecimal totalPrice) { 
        this.totalPrice = totalPrice; 
    }

    public int getItemCount() { 
        return itemCount; 
    }

    public void setItemCount(int itemCount) { 
        this.itemCount = itemCount; 
    }

    public static class CartItemDTO {
        private Long id;
        private Long productId;
        private String productName;
        private String productsku;
        private BigDecimal price;
        private int quantity;
        private BigDecimal subtotal;

        // Getters and Setters
        public Long getId() { 
            return id; 
        }

        public void setId(Long id) { 
            this.id = id; 
        }

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

        public String getProductsku() { 
            return productsku; 
        }
        public void setProductsku(String productsku) { 
            this.productsku = productsku; 
        }

        public BigDecimal getPrice() { 
            return price; 
        }

        public void setPrice(BigDecimal price) { 
            this.price = price; 
        }

        public int getQuantity() { 
            return quantity; 
        }

        public void setQuantity(int quantity) { 
            this.quantity = quantity; 
        }

        public BigDecimal getSubtotal() { 
            return subtotal; 
        }

        public void setSubtotal(BigDecimal subtotal) { 
            this.subtotal = subtotal; 
        }
    }
    
}
