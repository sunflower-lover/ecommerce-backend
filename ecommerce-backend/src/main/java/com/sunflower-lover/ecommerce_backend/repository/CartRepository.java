package com.sunflower_lover.ecommerce_backend.repository;

import com.sunflower_lover.ecommerce_backend.entity.Cart;
import com.sunflower_lover.ecommerce_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    boolean existsByUser(User user);
}

