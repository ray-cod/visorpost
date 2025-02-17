package com.raimi_dikamona.visorpost.Controllers;

import com.raimi_dikamona.visorpost.models.Post;
import com.raimi_dikamona.visorpost.models.User;
import com.raimi_dikamona.visorpost.services.PostService;
import com.raimi_dikamona.visorpost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final UserService userService;
    private final PostService postService;

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
            model.addAttribute("fragmentName", "home");
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{userName}")
    public String userProfile(
            @PathVariable String userName,
            Model model){
        Optional<User> user = userService.getUserByFirstname(userName.split("-")[0]);

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("fragmentName", "profile");
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/blogs")
    public String blog(Model model, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            List<Post> posts = postService.getAllPosts();
            model.addAttribute("user", user.get());
            model.addAttribute("posts", posts);
            model.addAttribute("fragmentName", "blogs");
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    // Authentication Pages

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
