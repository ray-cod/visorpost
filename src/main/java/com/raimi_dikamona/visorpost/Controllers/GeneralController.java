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

    // todo: I need to fix the method
    @GetMapping("/")
    public String homePage(Model model, Authentication authentication) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("The email is: " + email);

        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            return "redirect:/login";
        }

        return "index";
    }
}
