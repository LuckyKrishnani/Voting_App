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
    public ResponseEntity<Candidate> createCandidate(@PathVariable UUID electionId, @RequestBody CreateCandidateRequest request) {
        return ResponseEntity.ok(candidateService.createCandidate(electionId, request));
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<CandidateResponse>> getCandidatesByElection(@PathVariable UUID electionId) {
        return ResponseEntity.ok(candidateService.getCandidatesByElection(electionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }
}
