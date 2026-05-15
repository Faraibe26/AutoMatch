package com.automatch.controllers;

import com.automatch.services.AuthService;
import com.automatch.dto.LoginRequest;
import com.automatch.dto.RegisterRequest;
import com.automatch.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * AuthController - Handles authentication endpoints.
 * Provides login and registration endpoints.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * POST /auth/register - Register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /auth/login - Login user.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /auth/me - Get current user info (requires token).
     */
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@RequestHeader("Authorization") String token) {
        // Token is validated by JwtAuthFilter
        // This endpoint confirms user is authenticated
        AuthResponse response = new AuthResponse();
        response.setMessage("Authenticated");
        return ResponseEntity.ok(response);
    }
}
