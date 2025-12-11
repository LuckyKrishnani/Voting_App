package com.example.votingbank.service;

import com.example.votingbank.dto.CreateCandidateRequest;
import com.example.votingbank.dto.CandidateResponse;
import com.example.votingbank.model.Candidate;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    Candidate createCandidate(UUID electionId, CreateCandidateRequest request);
    List<CandidateResponse> getCandidatesByElection(UUID electionId);
    Candidate getCandidateById(UUID id);
}
