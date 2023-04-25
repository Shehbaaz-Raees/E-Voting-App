package com.example.votingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.votingapp.entity.Admin;
import com.example.votingapp.entity.Candidate;
import com.example.votingapp.repository.CandidateRepository;
import com.example.votingapp.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CandidateRepository candidateRepository; 
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/login")
	public String loginAdmin(@RequestParam("username") String username,
	                        @RequestParam("password") String password,
	                        HttpSession session,
	                        RedirectAttributes redirectAttributes) {
	    Admin existingUser = adminService.getUserByUsername(username);
	    if (existingUser == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
	        return "redirect:/";
	    }

	    if (!existingUser.getPassword().equals(password)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
	        return "redirect:/";
	    }

	    session.setAttribute("username", username);
	    return "redirect:/admin";
	}
	
	@PostMapping("/register")
    public String addNew(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
        adminService.addNew(admin);
        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/";
    }

	@GetMapping("/votecounts")
	public String getVoteCounts(Model model) {
	    List<Candidate> candidates = candidateRepository.findAll();
	    model.addAttribute("candidates", candidates);
	    return "admin";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	}

}