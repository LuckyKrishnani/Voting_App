package com.example.votingbank.dto;

import lombok.*;

import java.util.UUID;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private UUID id;
    private UUID electionId;
    private UUID candidateId;
    private UUID userId;
    private OffsetDateTime castAt;
    private Boolean isCounted;
}
