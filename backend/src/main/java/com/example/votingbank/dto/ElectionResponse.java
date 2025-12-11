package com.example.votingbank.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResponse {
    private UUID id;
    private String title;
    private String description;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    private Boolean isPublished;
    private Boolean isClosed;
}
