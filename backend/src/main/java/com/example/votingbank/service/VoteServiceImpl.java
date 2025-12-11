package com.example.votingbank.service;

import com.example.votingbank.dto.VoteRequest;
import com.example.votingbank.model.Candidate;
import com.example.votingbank.model.Election;
import com.example.votingbank.model.Users;
import com.example.votingbank.model.Vote;
import com.example.votingbank.repository.CandidateRepository;
import com.example.votingbank.repository.ElectionRepository;
import com.example.votingbank.repository.UserRepository;
import com.example.votingbank.repository.VoteRepository;
import com.example.votingbank.service.EncryptionService;
import com.example.votingbank.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    @Override
    public Vote castVote(VoteRequest request, UUID userId) {
        Users user = userRepository.findById(userId).orElseThrow();
        Election election = electionRepository.findById(request.getElectionId()).orElseThrow();
        Candidate candidate = candidateRepository.findById(request.getCandidateId()).orElseThrow();

        // Check if user already voted
        voteRepository.findByElectionAndUser(election, user).ifPresent(v -> {
            throw new RuntimeException("User has already voted in this election");
        });

        String ballotData = userId.toString() + election.getId().toString() + candidate.getId().toString();
        String ballotHash = HashUtil.sha256(ballotData);
        byte[] encryptedBallot = encryptionService.encrypt(ballotData);

        Vote vote = new Vote();
        vote.setElection(election);
        vote.setCandidate(candidate);
        vote.setUser(user);
        vote.setBallotHash(ballotHash);
        vote.setEncryptedBallot(encryptedBallot);

        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getVotesByElection(UUID electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow();
        return voteRepository.findByElection(election);
    }

    @Override
    public List<Vote> getVotesByUser(UUID userId) {
        Users user = userRepository.findById(userId).orElseThrow();
        return voteRepository.findByUser(user);
    }
}
