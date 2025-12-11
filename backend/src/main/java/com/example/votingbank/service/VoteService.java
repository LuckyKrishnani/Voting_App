package com.example.votingbank.service;

import com.example.votingbank.dto.VoteRequest;
import com.example.votingbank.model.Vote;

import java.util.List;
import java.util.UUID;

public interface VoteService {
    Vote castVote(VoteRequest request, UUID userId);
    List<Vote> getVotesByElection(UUID electionId);
    List<Vote> getVotesByUser(UUID userId);
}
