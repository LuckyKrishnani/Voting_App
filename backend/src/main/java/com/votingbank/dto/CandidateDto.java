package com.votingbank.dto;

import com.votingbank.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO for candidate data transfer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Integer position;

    private Long voteCount;

    public static CandidateDto from(Candidate candidate) {
        return CandidateDto.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .position(candidate.getPosition())
                .voteCount(candidate.getVoteCount())
                .build();
    }
}
