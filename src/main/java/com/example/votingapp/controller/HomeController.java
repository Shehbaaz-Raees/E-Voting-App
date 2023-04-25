package com.example.votingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage() {
    	return "user";
    }
        
    @GetMapping("/admin")
    public String showAdminPage() {
    	return "admin";
    }

    
    @GetMapping("/userlogin")
    public String showUserLoginPage() {
    return "userlogin";
    }

    @GetMapping("/adminlogin")
    public String showAdminLoginPage() {
    return "adminlogin";
    }
    
    @GetMapping("/adminregister")
    public String showAdminRegistrationPage() {
    	return "adminregister";
    }	

    @GetMapping("/userregister")
    public String showUserRegistrationPage() {
    	return "userregister";
    }	
}