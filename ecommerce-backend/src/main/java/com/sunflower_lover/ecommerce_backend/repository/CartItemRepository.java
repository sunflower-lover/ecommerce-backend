package com.sunflower_lover.ecommerce_backend.repository;

import com.sunflower_lover.ecommerce_backend.entity.Cart;
import com.sunflower_lover.ecommerce_backend.entity.CartItem;
import com.sunflower_lover.ecommerce_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteByCartAndProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);

}
