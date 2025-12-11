package com.example.votingbank.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {
    private UUID id;
    private String name;
    private String description;
    private String metadata; // JSON as String
    private UUID electionId;
}
