package com.example.votingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votingapp.entity.Candidate;
import com.example.votingapp.repository.CandidateRepository;

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
    
    public Candidate getCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        return optionalCandidate.orElse(null);
    }
    
    public void updateCandidateVotesCount(Candidate candidate, int votes) {
        candidate.setVotesCount(candidate.getVotesCount() + votes);
        candidateRepository.save(candidate);
    }

}
