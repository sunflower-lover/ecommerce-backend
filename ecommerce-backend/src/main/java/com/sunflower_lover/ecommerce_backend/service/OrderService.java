package com.sunflower_lover.ecommerce_backend.service;

import com.sunflower_lover.ecommerce_backend.dto.OrderRequest;
import com.sunflower_lover.ecommerce_backend.dto.OrderResponse;
import com.sunflower_lover.ecommerce_backend.entity.*;
import com.sunflower_lover.ecommerce_backend.repository.OrderRepository;
import com.sunflower_lover.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductRepository productRepository;
    
    // CREATE - Create order from cart
    @Transactional
    public OrderResponse createOrder(User user, OrderRequest request) {
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create order: Cart is empty");
        }
        
        // Verify stock availability
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() +
                    ". Available: " + product.getStockQuantity());
            }
        }
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setBillingAddress(request.getBillingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setShippingMethod(request.getShippingMethod() != null ? request.getShippingMethod() : "STANDARD");
        order.setNotes(request.getNotes());
        
        // Convert cart items to order items and update stock
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(cartItem.getProduct(), cartItem.getQuantity());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
            
            // Update product stock
            Product product = cartItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }
        
        // Calculate totals
        order.calculateTotals();
        
        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cartService.clearCart(user);
        
        return convertToResponse(savedOrder);
    }
    
    // READ - Get order by ID with user validation
    @SuppressWarnings("null")
    public OrderResponse getOrderById(Long id, User user) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        // Check if order belongs to user (if user is not null for admin)
        if (user != null && !order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to view this order");
        }
        
        return convertToResponse(order);
    }
    
    // READ - Get order by order number (public - no user check)
    public OrderResponse getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new RuntimeException("Order not found with number: " + orderNumber));
        return convertToResponse(order);
    }
    
    // READ - Get user's orders with pagination
    public Page<OrderResponse> getUserOrders(User user, Pageable pageable) {
        return orderRepository.findByUser(user, pageable)
            .map(this::convertToResponse);
    }
    
    // READ - Get all user orders as list
    public List<OrderResponse> getAllUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    // UPDATE - Cancel order
    @SuppressWarnings("null")
    @Transactional
    public OrderResponse cancelOrder(Long id, User user) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        // Check permission
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to cancel this order");
        }
        
        // Check if order can be cancelled
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Order cannot be cancelled. Current status: " + order.getStatus());
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        
        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        
        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }
    
    // ADMIN - Update order status
    @SuppressWarnings("null")
    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(status);
        
        // If cancelling, restore stock
        if (status == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        
        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }
    
    // ADMIN - Update payment status
    @SuppressWarnings("null")
    @Transactional
    public OrderResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setPaymentStatus(paymentStatus);
        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }
    
    // ADMIN - Update tracking number
    @SuppressWarnings("null")
    @Transactional
    public OrderResponse updateTrackingNumber(Long id, String trackingNumber) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setTrackingNumber(trackingNumber);
        
        // If tracking number added and status is PENDING, update to SHIPPED
        if (trackingNumber != null && !trackingNumber.isEmpty() && order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.SHIPPED);
        }
        
        Order updatedOrder = orderRepository.save(order);
        return convertToResponse(updatedOrder);
    }
    
    // ADMIN - Get all orders with pagination
    @SuppressWarnings("null")
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
            .map(this::convertToResponse);
    }
    
    // ADMIN - Get orders by status
    public Page<OrderResponse> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
            .map(this::convertToResponse);
    }
    
    
    
    // Convert Entity to Response DTO
    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        response.setCustomerEmail(order.getUser().getEmail());
        response.setSubtotal(order.getSubtotal());
        response.setTax(order.getTax());
        response.setShippingCost(order.getShippingCost());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus().name());
        response.setPaymentStatus(order.getPaymentStatus().name());
        response.setShippingAddress(order.getShippingAddress());
        response.setBillingAddress(order.getBillingAddress());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setShippingMethod(order.getShippingMethod());
        response.setTrackingNumber(order.getTrackingNumber());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        
        response.setItems(order.getItems().stream()
            .map(this::convertToItemDTO)
            .collect(Collectors.toList()));
        
        return response;
    }
    
    // Convert OrderItem to DTO
    private OrderResponse.OrderItemDTO convertToItemDTO(OrderItem item) {
        OrderResponse.OrderItemDTO dto = new OrderResponse.OrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductSku(item.getProduct().getSku());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}