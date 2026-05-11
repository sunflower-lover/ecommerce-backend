package com.sunflower_lover.ecommerce_backend.repository;

import com.sunflower_lover.ecommerce_backend.entity.Order;
import com.sunflower_lover.ecommerce_backend.entity.OrderStatus;
import com.sunflower_lover.ecommerce_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    Page<Order> findByUser(User user, Pageable pageable);
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime dateTime);
    
    @Query("SELECT o FROM Order o WHERE o.user.email = :email")
    List<Order> findByUserEmail(@Param("email") String email);
    
    long countByUserAndStatus(User user, OrderStatus status);
}