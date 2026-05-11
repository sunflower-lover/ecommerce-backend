package com.sunflower_lover.ecommerce_backend.config;

import com.sunflower_lover.ecommerce_backend.entity.Role;
import com.sunflower_lover.ecommerce_backend.entity.User;
import com.sunflower_lover.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {

        // Create admin user if not exists
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✅ Admin user created - Email: admin@gmail.com, Password: admin123");
        }
        
        // Create a test user if not exists
        if (!userRepository.existsByEmail("user@gmail.com")) {
            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFirstName("Test");
            user.setLastName("User");
            user.setRole(Role.USER);
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("Test user created - Email: user@gmail.com, Password: user123");
        }
    }
}
