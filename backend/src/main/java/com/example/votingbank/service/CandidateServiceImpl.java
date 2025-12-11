package com.example.votingbank.service;

import com.example.votingbank.dto.CandidateResponse;
import com.example.votingbank.dto.CreateCandidateRequest;
import com.example.votingbank.model.Candidate;
import com.example.votingbank.model.Election;
import com.example.votingbank.repository.CandidateRepository;
import com.example.votingbank.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    @Override
    public Candidate createCandidate(UUID electionId, CreateCandidateRequest request) {
        Election election = electionRepository.findById(electionId).orElseThrow();
        Candidate candidate = new Candidate();
        candidate.setElection(election);
        candidate.setName(request.getName());
        candidate.setDescription(request.getDescription());
        candidate.setMetadata(request.getMetadata());
        return candidateRepository.save(candidate);
    }

    @Override
    public List<CandidateResponse> getCandidatesByElection(UUID electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow();
        return candidateRepository.findByElection(election).stream()
                .map(c -> new CandidateResponse(c.getId(), c.getName(), c.getDescription(), c.getMetadata(), election.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Candidate getCandidateById(UUID id) {
        return candidateRepository.findById(id).orElseThrow();
    }
}
