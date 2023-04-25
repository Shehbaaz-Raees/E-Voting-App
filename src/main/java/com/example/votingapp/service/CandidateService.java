package com.example.votingapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votingapp.entity.Candidate;
import com.example.votingapp.repository.CandidateRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CandidateService {
    
    private final CandidateRepository candidateRepository;
    
    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    
    @PostConstruct
    public void addHardcodedCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.isEmpty()) {
            List<Candidate> hardcodedCandidates = new ArrayList<>();
            hardcodedCandidates.add(new Candidate("James Bond"));
            hardcodedCandidates.add(new Candidate("Ethan Hunt"));
            hardcodedCandidates.add(new Candidate("Leo Dicaprio"));
            hardcodedCandidates.add(new Candidate("Tom Cruise"));

            candidateRepository.saveAll(hardcodedCandidates);
        }
    }

    public Candidate getCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        return optionalCandidate.orElse(null);
    }
    
    public void updateCandidateVotesCount(Candidate candidate, int votes) {
        candidate.setVotesCount(candidate.getVotesCount() + votes);
        candidateRepository.save(candidate);
    }

}