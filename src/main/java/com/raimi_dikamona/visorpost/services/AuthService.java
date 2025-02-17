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

    /**
     * Authenticates a user and sets the JWT token in an HTTP-only cookie for security.
     *
     * @param request  The authentication request containing email and password.
     * @param response The HTTP response where the authentication cookie will be set.
     * @return AuthenticationResponse containing the generated JWT token.
     */
    public AuthenticationResponse Authentication(AuthenticationRequest request,
                                 HttpServletResponse response) {

        AuthenticationResponse authResponse = this.authenticate(request);
        String token = "Bearer-" + authResponse.getToken();
        Cookie cookie = new Cookie("authenticate", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set to true for HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(12 * 60 * 60); // 12 hours
        response.addCookie(cookie);

        return authResponse;
    }

    /**
     * Deletes an authentication cookie by setting its max age to zero.
     *
     * @param cookieName The name of the cookie to be deleted.
     * @param response   The HTTP response where the deletion request will be applied.
     */
    public void deleteCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie); // Add the cookie to the response to delete it
    }
}
