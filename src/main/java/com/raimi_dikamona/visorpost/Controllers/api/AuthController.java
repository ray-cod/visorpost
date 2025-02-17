package com.raimi_dikamona.visorpost.Controllers.api;

import com.raimi_dikamona.visorpost.Controllers.authUtils.*;
import com.raimi_dikamona.visorpost.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public void register(@ModelAttribute RegisterRequest request,
                           HttpServletResponse response) throws IOException {
        AuthenticationResponse regResponse = service.register(request);
        response.sendRedirect("/login");
    }

    @PostMapping("/authenticate")
    public void authenticate(@ModelAttribute AuthenticationRequest request,
                                               HttpServletResponse response) throws IOException {
        AuthenticationResponse authResponse = service.Authentication(request, response);
        response.sendRedirect("/");
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }
}
