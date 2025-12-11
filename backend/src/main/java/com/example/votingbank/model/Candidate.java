package com.example.votingbank.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @Column(nullable = false)
    private String name;

    private String description;

    private String metadata; // JSON stored as plain String

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<CandidateMedia> media;

    @OneToMany(mappedBy = "candidate")
    private List<Vote> votes;
}
