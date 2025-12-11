package com.example.votingbank.repository;

import com.example.votingbank.model.Candidate;
import com.example.votingbank.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    List<Candidate> findByElection(Election election);
}
