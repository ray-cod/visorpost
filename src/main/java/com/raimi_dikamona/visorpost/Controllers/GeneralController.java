package com.raimi_dikamona.visorpost.Controllers;

import com.raimi_dikamona.visorpost.models.User;
import com.raimi_dikamona.visorpost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GeneralController {

    private final UserService userService;

    @GetMapping("/")
    public String homePage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName(); // Ensure JWT filter properly sets this
        System.out.println("The email is: " + email);

        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "index";
        } else {
            return "redirect:/login";
        }
    }
}
