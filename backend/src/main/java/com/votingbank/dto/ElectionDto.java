package com.votingbank.dto;

import com.votingbank.entity.Election;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for election data transfer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long createdBy;

    private Set<CandidateDto> candidates;

    private Long totalVotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ElectionDto from(Election election) {
        return ElectionDto.builder()
                .id(election.getId())
                .title(election.getTitle())
                .description(election.getDescription())
                .status(election.getStatus().toString())
                .startTime(election.getStartTime())
                .endTime(election.getEndTime())
                .createdBy(election.getCreatedBy().getId())
                .candidates(election.getCandidates().stream()
                        .map(CandidateDto::from)
                        .collect(Collectors.toSet()))
                .totalVotes((long) election.getVotes().size())
                .createdAt(election.getCreatedAt())
                .updatedAt(election.getUpdatedAt())
                .build();
    }
}
