package com.sunflower_lover.ecommerce_backend.controller;

import com.sunflower_lover.ecommerce_backend.dto.OrderResponse;
import com.sunflower_lover.ecommerce_backend.dto.UserDTO;
import com.sunflower_lover.ecommerce_backend.entity.OrderStatus;
import com.sunflower_lover.ecommerce_backend.entity.PaymentStatus;
import com.sunflower_lover.ecommerce_backend.entity.Role;
import com.sunflower_lover.ecommerce_backend.entity.User;
import com.sunflower_lover.ecommerce_backend.repository.UserRepository;
import com.sunflower_lover.ecommerce_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @SuppressWarnings("null")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return ResponseEntity.ok(convertToDTO(user));
    }
    
    // Update user role 
    @SuppressWarnings("null")
    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(
            @PathVariable Long id,
            @RequestParam String role) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setRole(Role.valueOf(role.toUpperCase()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(convertToDTO(savedUser));
    }
    
    // Enable/disable user
    @SuppressWarnings("null")
    @PutMapping("/users/{id}/status")
    public ResponseEntity<UserDTO> toggleUserStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setEnabled(enabled);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(convertToDTO(savedUser));
    }
    
    @SuppressWarnings("null")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
    
    
    
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }
    
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<Page<OrderResponse>> getOrdersByStatus(
            @PathVariable OrderStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status, pageable));
    }
    
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
    
    @PutMapping("/orders/{id}/payment")
    public ResponseEntity<OrderResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus paymentStatus) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(id, paymentStatus));
    }
    
    @PutMapping("/orders/{id}/tracking")
    public ResponseEntity<OrderResponse> addTrackingNumber(
            @PathVariable Long id,
            @RequestParam String trackingNumber) {
        return ResponseEntity.ok(orderService.updateTrackingNumber(id, trackingNumber));
    }
    
    
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalOrders(orderService.getAllOrders(Pageable.unpaged()).getTotalElements());
        stats.setPendingOrders(orderService.getOrdersByStatus(OrderStatus.PENDING, Pageable.unpaged()).getTotalElements());
        stats.setShippedOrders(orderService.getOrdersByStatus(OrderStatus.SHIPPED, Pageable.unpaged()).getTotalElements());
        stats.setDeliveredOrders(orderService.getOrdersByStatus(OrderStatus.DELIVERED, Pageable.unpaged()).getTotalElements());
        stats.setCancelledOrders(orderService.getOrdersByStatus(OrderStatus.CANCELLED, Pageable.unpaged()).getTotalElements());
        
        return ResponseEntity.ok(stats);
    }
    
    // Helper methods
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole().name());
        dto.setEnabled(user.isEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
    
    // Dashboard Stats DTO
    public static class DashboardStats {
        private long totalUsers;
        private long totalOrders;
        private long pendingOrders;
        private long shippedOrders;
        private long deliveredOrders;
        private long cancelledOrders;
        
        public long getTotalUsers() { 
            return totalUsers; 
        }

        public void setTotalUsers(long totalUsers) { 
            this.totalUsers = totalUsers; 
        }
        
        public long getTotalOrders() { 
            return totalOrders; 
        }

        public void setTotalOrders(long totalOrders) {
            this.totalOrders = totalOrders; 
        }
        
        public long getPendingOrders() {
            return pendingOrders; 
        }

        public void setPendingOrders(long pendingOrders) {
            this.pendingOrders = pendingOrders; 
        }
        
        public long getShippedOrders() { 
            return shippedOrders; 
        }

        public void setShippedOrders(long shippedOrders) { 
            this.shippedOrders = shippedOrders; 
        }
        
        public long getDeliveredOrders() { 
            return deliveredOrders; 
        }
        
        public void setDeliveredOrders(long deliveredOrders) { 
            this.deliveredOrders = deliveredOrders; 
        }
        
        public long getCancelledOrders() { 
            return cancelledOrders; 
        }

        public void setCancelledOrders(long cancelledOrders) { 
            this.cancelledOrders = cancelledOrders; 
        }
    }
}