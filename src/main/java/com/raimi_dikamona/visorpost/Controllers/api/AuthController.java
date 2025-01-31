package com.raimi_dikamona.visorpost.Controllers.api;

import com.raimi_dikamona.visorpost.Controllers.authUtils.*;
import com.raimi_dikamona.visorpost.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request){
        AuthenticationResponse response = service.register(request);
        return "redirect:/login";
    }

    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute AuthenticationRequest request,
                                               HttpServletResponse response){
        response.addCookie(service.generateCookie(request));
        return "redirect:/";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(){
        return "redirect:/login";
    }
}
