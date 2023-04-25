package com.example.votingapp.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.votingapp.entity.Candidate;
import com.example.votingapp.entity.User;
import com.example.votingapp.repository.CandidateRepository;
import com.example.votingapp.repository.UserRepository;
import com.example.votingapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	CandidateRepository candidateRepository; 

	@Autowired
	UserRepository userRepository; 
	
	@Autowired
    UserService userService;
	
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

	    if (!existingUser.getPassword().equals(password)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
	        return "redirect:/";
	    }

	    session.setAttribute("username", username);
	    return "redirect:/user";
	}
	
    @PostMapping("/register")
    public String addNew(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.addNew(user);
        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/";
    }


	@GetMapping("/hasvoted")
	public String getHasVotedPage() {
	    return "has-voted";
	}

	@PostMapping("/vote")
	public String vote(@RequestParam("candidateId") Long candidateId, HttpSession session, Model model) {
	    Candidate candidate = candidateRepository.findById(candidateId)
	            .orElseThrow(() -> new NoSuchElementException("Candidate with id " + candidateId + " not found"));
	    String username = (String) session.getAttribute("username");
	    if (username == null) {
	        model.addAttribute("message", "You must be logged in to vote.");
	        return "vote-failure";
	    }
	    User user = userRepository.findByUsername(username);
	    if (user.isHasVoted()) {
	        model.addAttribute("message", "You have already voted for a candidate.");
	        return "vote-failure";
	    }
	    Integer votesCount = candidate.getVotesCount();
	    candidate.setVotesCount(votesCount + 1);
	    candidateRepository.save(candidate);
	    user.setHasVoted(true);
	    userRepository.save(user);
	    model.addAttribute("message", "Thank you for voting!");
	    return "vote-success";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    return "redirect:/";
	}
}