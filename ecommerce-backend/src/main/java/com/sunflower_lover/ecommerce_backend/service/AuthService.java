package com.sunflower_lover.ecommerce_backend.service;

import com.sunflower_lover.ecommerce_backend.dto.AuthRequest;
import com.sunflower_lover.ecommerce_backend.dto.AuthResponse;
import com.sunflower_lover.ecommerce_backend.dto.RegisterRequest;
import com.sunflower_lover.ecommerce_backend.entity.Product;
import com.sunflower_lover.ecommerce_backend.entity.User;
import com.sunflower_lover.ecommerce_backend.exception.DuplicateResourceException;
import com.sunflower_lover.ecommerce_backend.repository.UserRepository;
import com.sunflower_lover.ecommerce_backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
        throw new DuplicateResourceException("User", "email", request.getEmail());
    }
        
        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        
        userRepository.save(user);
        
        // Generate token
        String token = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        
        return new AuthResponse(token, user.getEmail(), "USER", "Registration successful!");
    }
    
    public AuthResponse login(AuthRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        // Generate token
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(user);
        
        return new AuthResponse(token, request.getEmail(), 
            user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", ""),
            "Login successful!");
    }

    public void login(User user) {
        log.info("User role: {}, Login successful!", user.getAuthorities().iterator().next().getAuthority());
    }
    
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public void createProduct (Product product) {
        log.info("Product created: {}", product.getName());
    }
}
