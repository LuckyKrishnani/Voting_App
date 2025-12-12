package com.example.votingbank.controller;

import com.example.votingbank.dto.CreateElectionRequest;
import com.example.votingbank.dto.ElectionResponse;
import com.example.votingbank.model.Election;
import com.example.votingbank.service.ElectionService;
import com.example.votingbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Election> createElection(@RequestBody CreateElectionRequest request) {
        // For testing without authentication, use the first user or create a default admin
        UUID creatorId = userService.getAllUsers().stream()
                .findFirst()
                .map(user -> user.getId())
                .orElse(null);
        
        if (creatorId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(electionService.createElection(request, creatorId));
    }

    @GetMapping
    public ResponseEntity<List<ElectionResponse>> getAllElections() {
        return ResponseEntity.ok(electionService.getAllElections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Election> getElection(@PathVariable UUID id) {
        return ResponseEntity.ok(electionService.getElectionById(id));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> closeElection(@PathVariable UUID id) {
        electionService.closeElection(id);
        return ResponseEntity.ok().build();
    }
}
