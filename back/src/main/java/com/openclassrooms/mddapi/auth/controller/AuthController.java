package com.openclassrooms.mddapi.auth.controller;

import com.openclassrooms.mddapi.auth.DTO.AuthResponse;
import com.openclassrooms.mddapi.auth.DTO.LoginRequest;
import com.openclassrooms.mddapi.auth.DTO.RegisterRequest;
import com.openclassrooms.mddapi.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller responsible for authentication operations.
 * <p>
 * Provides endpoints for user login and registration.
 * Successful authentication results in the issuance of a JWT token.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Authenticates a user using their credentials.
     *
     * @param request the login request containing user credentials
     * @return an authentication response containing a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new user.
     * <p>
     * The provided data is validated before user creation.
     * Upon successful registration, a JWT token is issued.
     *
     * @param request the registration request containing user information
     * @return an authentication response containing a JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }


}
