package com.example.votingbank.controller;

import com.example.votingbank.dto.CandidateResponse;
import com.example.votingbank.dto.CreateCandidateRequest;
import com.example.votingbank.model.Candidate;
import com.example.votingbank.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/{electionId}")
    public ResponseEntity<CandidateResponse> createCandidate(@PathVariable UUID electionId, @RequestBody CreateCandidateRequest request) {
        Candidate candidate = candidateService.createCandidate(electionId, request);
        CandidateResponse response = new CandidateResponse(
            candidate.getId(),
            candidate.getName(),
            candidate.getDescription(),
            candidate.getMetadata(),
            electionId
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<CandidateResponse>> getCandidatesByElection(@PathVariable UUID electionId) {
        return ResponseEntity.ok(candidateService.getCandidatesByElection(electionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidate(@PathVariable UUID id) {
        Candidate candidate = candidateService.getCandidateById(id);
        CandidateResponse response = new CandidateResponse(
            candidate.getId(),
            candidate.getName(),
            candidate.getDescription(),
            candidate.getMetadata(),
            candidate.getElection().getId()
        );
        return ResponseEntity.ok(response);
    }
}
