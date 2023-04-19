package com.example.votingapp.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.votingapp.entity.Candidate;
import com.example.votingapp.entity.User;
import com.example.votingapp.repository.CandidateRepository;
import com.example.votingapp.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	CandidateRepository candidateRepository; 

	@Autowired
	UserRepository userRepository; 
	
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
	
//	@GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/";
//    }
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
