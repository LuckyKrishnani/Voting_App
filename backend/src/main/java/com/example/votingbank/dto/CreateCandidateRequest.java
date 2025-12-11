package com.example.votingbank.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCandidateRequest {
    private String name;
    private String description;
    private String metadata; // JSON as String
}
