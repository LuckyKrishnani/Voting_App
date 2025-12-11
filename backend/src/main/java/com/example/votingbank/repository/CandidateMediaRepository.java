package com.example.votingbank.repository;

import com.example.votingbank.model.CandidateMedia;
import com.example.votingbank.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateMediaRepository extends JpaRepository<CandidateMedia, UUID> {
    List<CandidateMedia> findByCandidate(Candidate candidate);
}
