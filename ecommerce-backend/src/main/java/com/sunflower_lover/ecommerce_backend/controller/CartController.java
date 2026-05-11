package com.sunflower_lover.ecommerce_backend.controller;

import com.sunflower_lover.ecommerce_backend.dto.CartDTO;
import com.sunflower_lover.ecommerce_backend.entity.User;
import com.sunflower_lover.ecommerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    // Get current user's cart
    @GetMapping
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal User user) {
        CartDTO cart = cartService.getCart(user);
        return ResponseEntity.ok(cart);
    }
 
    //Adding a product to the cart
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(user, productId, quantity));
    }
    
    // Removing a product from the cart
    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeFromCart(user, productId));
    }

    // Clear the cart
    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
        return ResponseEntity.ok(cartService.clearCart(user));
    }
    
}
