package com.sunflower_lover.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower_lover.ecommerce_backend.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySku(String sku);
    
    boolean existsBySku(String sku);
    
    List<Product> findByActiveTrue();
    
    List<Product> findByCategory(String category);
    
    List<Product> findByPriceLessThan(BigDecimal price);
    
    List<Product> findByStockQuantityLessThan(int threshold);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stockQuantity = :quantity WHERE p.id = :id")
    int updateStockQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.active = false WHERE p.id = :id")
    int deactivateProduct(@Param("id") Long id);
}
