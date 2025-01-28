package com.raimi_dikamona.visorpost.Controllers.auth;

import com.raimi_dikamona.visorpost.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@ModelAttribute RegisterRequest request){
        // Register the user
        AuthenticationResponse response = service.register(request);

        // Redirect to login page after registration
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:8080/login")
                .build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@ModelAttribute AuthenticationRequest request){
        // Authenticate the user
        AuthenticationResponse response = service.authenticate(request);

        // Redirect to home page after login
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:8080/")
                .build();
    }


}
