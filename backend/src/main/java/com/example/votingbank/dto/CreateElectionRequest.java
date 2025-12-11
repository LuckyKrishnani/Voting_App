package com.example.votingbank.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateElectionRequest {
    private String title;
    private String description;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    private Boolean isPublished;
}
