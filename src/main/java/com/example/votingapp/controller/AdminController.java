package com.example.votingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.votingapp.entity.Candidate;
import com.example.votingapp.repository.CandidateRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private CandidateRepository candidateRepository; 
	
	@GetMapping("/admin")
	public String adminPage(Model model) {
	    List<Candidate> votes = candidateRepository.findAll();
	    model.addAttribute("votes", votes);
	    return "admin";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	}

}
