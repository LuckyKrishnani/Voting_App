package com.example.votingbank.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    private UUID electionId;
    private UUID candidateId;
}
