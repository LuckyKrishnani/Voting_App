package com.example.votingbank.repository;

import com.example.votingbank.model.Vote;
import com.example.votingbank.model.Election;
import com.example.votingbank.model.Users;
import com.example.votingbank.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    List<Vote> findByElection(Election election);
    List<Vote> findByUser(Users user);
    List<Vote> findByCandidate(Candidate candidate);
    Optional<Vote> findByElectionAndUser(Election election, Users user);
}
