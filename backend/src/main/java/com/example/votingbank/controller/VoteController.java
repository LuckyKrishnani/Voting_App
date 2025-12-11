package com.example.votingbank.controller;

import com.example.votingbank.dto.VoteRequest;
import com.example.votingbank.model.Vote;
import com.example.votingbank.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Vote> castVote(@RequestBody VoteRequest request, @RequestParam UUID userId) {
        return ResponseEntity.ok(voteService.castVote(request, userId));
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<Vote>> getVotesByElection(@PathVariable UUID electionId) {
        return ResponseEntity.ok(voteService.getVotesByElection(electionId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vote>> getVotesByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(voteService.getVotesByUser(userId));
    }
}
