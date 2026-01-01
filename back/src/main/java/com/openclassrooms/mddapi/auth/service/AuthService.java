package com.openclassrooms.mddapi.auth.service;

import com.openclassrooms.mddapi.auth.DTO.AuthResponse;
import com.openclassrooms.mddapi.auth.DTO.LoginRequest;
import com.openclassrooms.mddapi.auth.DTO.RegisterRequest;
import com.openclassrooms.mddapi.auth.jwt.JwtUtils;
import com.openclassrooms.mddapi.user.User;
import com.openclassrooms.mddapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authentication and authorization operations.
 * <p>
 * Handles user login, user registration, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * Authenticates a user using their credentials.
     * <p>
     * The user may authenticate using either their email or username.
     * Upon successful authentication, a JWT token is generated.
     *
     * @param request the login request containing authentication credentials
     * @return an authentication response containing a JWT token
     * @throws org.springframework.security.core.AuthenticationException
     *         if authentication fails
     */
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmailOrUsername(), request.getPassword())
        );

        String token = jwtUtils.generateToken(authentication);
        return new AuthResponse(token);
    }

    /**
     * Registers a new user.
     * <p>
     * Validates that the email and username are unique before creating the user.
     * The password is securely encoded before being persisted.
     * Upon successful registration, a JWT token is generated.
     *
     * @param request the registration request containing user information
     * @return an authentication response containing a JWT token
     * @throws RuntimeException if the email or username is already in use
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("username déjà utilisé !");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), request.getPassword()
        );

        String token = jwtUtils.generateToken(authentication);
        return new AuthResponse(token);
    }

    /**
     * Generates a new JWT token for an existing user.
     * <p>
     * This method is typically used after updating user information
     * to issue a fresh token reflecting the updated state.
     *
     * @param user the user for whom the token is generated
     * @return a newly generated JWT token
     */
    public String getTokenForUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null
        );

        return jwtUtils.generateToken(authentication);

    }
}
