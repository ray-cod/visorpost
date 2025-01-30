package com.raimi_dikamona.visorpost.Controllers.auth;

import com.raimi_dikamona.visorpost.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        String token = "Bearer-" + service.authenticate(request).getToken();
        System.out.println("The token is: " + token);
        Cookie cookie = new Cookie("authenticate", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set to true for HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(3 * 24 * 60 * 60);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(){
        return "redirect:/login";
    }
}
