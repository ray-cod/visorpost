package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.Controllers.authUtils.*;
import com.raimi_dikamona.visorpost.config.JwtService;
import com.raimi_dikamona.visorpost.models.*;
import com.raimi_dikamona.visorpost.models.enums.Role;
import com.raimi_dikamona.visorpost.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service class responsible for handling authentication and user registration logic.
 * <p>
 * This service provides methods for:
 * - Registering a new user.
 * - Authenticating a user with email and password.
 * - Generating and managing JWT tokens.
 * - Setting and deleting authentication cookies.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user by encoding the password and saving user details in the database.
     * Generates a JWT token upon successful registration.
     *
     * @param request The registration request containing user details.
     * @return AuthenticationResponse containing the generated JWT token.
     */
    public AuthenticationResponse register(RegisterRequest request){
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .bio("")
                .profilePicture("")
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .googleId("")
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user by verifying credentials and generating a JWT token.
     *
     * @param request The authentication request containing user email and password.
     * @return AuthenticationResponse containing the generated JWT token.
     * @throws NoSuchElementException if the user does not exist.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("The user with the email \""
                        + request.getEmail() + "\" doesn't exist"));
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
