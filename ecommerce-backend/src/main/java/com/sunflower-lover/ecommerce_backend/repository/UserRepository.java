package com.sunflower_lover.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sunflower_lover.ecommerce_backend.entity.User;
import java.util.List;
import java.util.Optional;
import com.sunflower_lover.ecommerce_backend.entity.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByEnabledTrue();
    List<User> findByRole(Role role);
}
