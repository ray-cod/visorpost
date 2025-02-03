package com.raimi_dikamona.visorpost.services;

import com.raimi_dikamona.visorpost.Controllers.authUtils.*;
import com.raimi_dikamona.visorpost.config.JwtService;
import com.raimi_dikamona.visorpost.models.*;
import com.raimi_dikamona.visorpost.models.enums.Role;
import com.raimi_dikamona.visorpost.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

    public Cookie generateCookie(AuthenticationRequest request) {

        String token = "Bearer-" + this.authenticate(request).getToken();
        Cookie cookie = new Cookie("authenticate", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set to true for HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(12 * 60 * 60); // 12 hours

        return cookie;
    }

    public void deleteCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie); // Add the cookie to the response to delete it
    }
}
