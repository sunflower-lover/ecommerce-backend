package com.sunflower_lover.ecommerce_backend.service;

import com.sunflower_lover.ecommerce_backend.dto.CartDTO;
import com.sunflower_lover.ecommerce_backend.entity.*;
import com.sunflower_lover.ecommerce_backend.repository.CartItemRepository;
import com.sunflower_lover.ecommerce_backend.repository.CartRepository; 
import com.sunflower_lover.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    public CartDTO getCart(User user) {
        Cart cart = getOrCreateCart(user);
        return convertToDTO(cart);
    }

    @Transactional
    public CartDTO addToCart(User user, Long productId, int quantity) {
        // Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Validate productId
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        // Get product and validate stock
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));


        
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStockQuantity());
        }

        // Get cart and check if product is already in cart
        Cart cart = getOrCreateCart(user);
        
        
        CartItem existingItem =cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update quantity if item already exists in cart
            int newQuantity = existingItem.getQuantity() + quantity;
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Insufficient stock. Total is: " + newQuantity);
            }
            existingItem.setQuantity(newQuantity);
        } else {
            // Add new item to cart
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);

        }

        // Update cart total price
        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDTO(cart);
    }

    // Additional methods for updating quantity, removing items, clearing cart, etc.
    @SuppressWarnings("null")
    @Transactional
    public CartDTO updateCartItem(User user, Long productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        Cart cart = getOrCreateCart(user);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity == 0) {
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock. Available: " + product.getStockQuantity());
            }
            cartItem.setQuantity(quantity);
        }

        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDTO(cart);
    }

    @SuppressWarnings("null")
    @Transactional
    public CartDTO removeFromCart(User user, Long productId) {
        Cart cart = getOrCreateCart(user);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return convertToDTO(cart);
    }

    // Clear the whole cart

    @SuppressWarnings("null")
    @Transactional
    public CartDTO clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            cartItemRepository.deleteAll(cart.getItems());
        }
        cart.getItems().clear();
        cart.calculateTotalPrice();
        cartRepository.save(cart);
        
        return convertToDTO(cart);
    }

    // Helper method to convert Cart entity to CartDTO
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setItemCount(cart.getItems().size());
        List<CartDTO.CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(this::convert)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        return dto;
    }

    public CartDTO.CartItemDTO convert(CartItem item) {
        CartDTO.CartItemDTO dto = new CartDTO.CartItemDTO();
            dto.setId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductsku(item.getProduct().getSku());
            dto.setPrice(item.getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
      
                
        return dto;
    }
}
