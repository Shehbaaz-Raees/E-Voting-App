package com.example.votingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.votingapp.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Custom query methods go here...

}
