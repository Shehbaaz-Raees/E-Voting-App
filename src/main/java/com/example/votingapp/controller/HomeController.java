package com.example.votingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.votingapp.entity.User;
import com.example.votingapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginPage(Model model, RedirectAttributes redirectAttributes) {
        if (redirectAttributes.getFlashAttributes().containsKey("message")) {
            model.addAttribute("errorMessage", redirectAttributes.getFlashAttributes().get("message"));
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
    	return "register";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
            return "redirect:/";
        }

        if (username.equals("admin") && password.equals("admin")) {
            session.setAttribute("username", "admin");
            return "redirect:/admin";
        }
        if (!existingUser.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
            return "redirect:/";
        }
        session.setAttribute("username", username);
        return "redirect:/user";
    }

    
    @GetMapping("/user")
    public String showUserPage() {
        return "user";
    }
    
    @PostMapping("/register")
    public String addNew(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.addNew(user);
        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/";
    }
}
