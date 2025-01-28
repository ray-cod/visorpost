package com.raimi_dikamona.visorpost.Controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPagesController {

    @GetMapping("/login")
    public String logIn(Model model){
        model.addAttribute("fragmentName", "login");
        return "auth";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("fragmentName", "register");
        return "auth";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("fragmentName", "forgot-password");
        return "auth";
    }

}
